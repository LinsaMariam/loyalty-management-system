package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.config.LoyaltyConstantsConfig;
import com.springboot.loyaltymanagementsystem.dto.FlightBookingRequest;
import com.springboot.loyaltymanagementsystem.dto.FlightBookingResponse;
import com.springboot.loyaltymanagementsystem.entity.Flight;
import com.springboot.loyaltymanagementsystem.entity.FlightBooking;
import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import com.springboot.loyaltymanagementsystem.exception.MemberNotFoundException;
import com.springboot.loyaltymanagementsystem.repository.FlightBookingRepository;
import com.springboot.loyaltymanagementsystem.repository.MemberPersonalDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FlightBookingService {

    private final MemberPersonalDetailsRepository memberRepo;
    private final FlightService flightService;
    private final FlightBookingRepository bookingRepo;
    private final LoyaltyConstantsConfig constants;

    public FlightBookingResponse bookFlight(FlightBookingRequest request) {
        MemberPersonalDetails member = memberRepo.findByMembershipNumber(request.getMembershipNumber());
        if (member == null) throw new MemberNotFoundException("Member not found");

        Flight flight = flightService.getFlightById(request.getFlightId());
        if (flight.getAvailableSeats() <= 0) throw new RuntimeException("No seats available");

        double originalPrice = flight.getPrice();
        float pointsUsed = request.isUsePoints() ? Math.min(member.getRewardPoints(), (float) originalPrice) : 0f;

        double finalPrice = originalPrice - pointsUsed;
        if (finalPrice < 0) throw new RuntimeException("Invalid price calculation");

        float earned = switch (member.getLoyaltyTier()) {
            case "Gold" -> Math.min((float) originalPrice * constants.getGOLD_RATE(), constants.getGOLD_MAX_POINTS());
            case "Silver" -> Math.min((float) originalPrice * constants.getSILVER_RATE(), constants.getSILVER_MAX_POINTS());
            default -> Math.min((float) originalPrice * constants.getDEFAULT_RATE(), constants.getDEFAULT_MAX_POINTS());
        };

        float updatedPoints = Math.max(0, member.getRewardPoints() - pointsUsed) + earned;
        member.setRewardPoints(updatedPoints);

        if (updatedPoints >= constants.getGOLD_MEMBER_POINTS()) {
            member.setLoyaltyTier("Gold");
        } else if (updatedPoints >= constants.getSILVER_MEMBER_POINTS()) {
            member.setLoyaltyTier("Silver");
        } else {
            member.setLoyaltyTier(constants.getINITIAL_TIER());
        }

        memberRepo.save(member);
        flightService.updateFlightSeats(String.valueOf(flight.getId()), 1);

        FlightBooking booking = FlightBooking.builder()
                .membershipNumber(member.getMembershipNumber())
                .flight(flight)
                .originalPrice(originalPrice)
                .discountedPrice(finalPrice)
                .pointsUsed((double) pointsUsed)
                .pointsEarned((double) earned)
                .status("Confirmed")
                .bookingDate(new Date())
                .build();

        bookingRepo.save(booking);

        return FlightBookingResponse.builder()
                .bookingId(booking.getId())
                .membershipNumber(member.getMembershipNumber())
                .flightNumber(flight.getFlightNumber())
                .originalPrice(originalPrice)
                .discountedPrice(finalPrice)
                .pointsUsed(pointsUsed)
                .pointsEarned(earned)
                .status("Confirmed")
                .build();
    }
}
