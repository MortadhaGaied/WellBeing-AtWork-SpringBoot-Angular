package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.UsersOffer;
import com.wellbeignatwork.backend.model.UsersOfferKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UsersOfferRepo extends JpaRepository<UsersOffer, UsersOfferKey> {

    List<UsersOffer> findByUserIdAndIsFavoriteTrue(Long userId);

    List<UsersOffer> findByUserIdAndIsInvitedTrue(Long userId);

    List<UsersOffer> findByUserIdAndIsAcceptedTrue(Long userId);

    List<UsersOffer> findByOfferIdAndIsAcceptedTrue(Long idOffer);

    List<UsersOffer> findByOfferIdAndIsInvitedTrue(Long idOffer);

    UsersOffer findByIdUserIdAndOfferId(Long userId, Long idOffer);

    @Query("SELECT o FROM UsersOffer o WHERE o.isAccepted = true AND o.offer.starDateOf >= ?1 AND o.offer.starDateOf <= ?2")
    List<UsersOffer> getByOfferStartDate(LocalDateTime inf, LocalDateTime sup);

    @Query("SELECT o FROM UsersOffer o WHERE o.isAccepted = true AND o.offer.endDateOf >= ?1 AND o.offer.endDateOf <= ?2")
    List<UsersOffer> getByOfferEndDate(LocalDateTime inf, LocalDateTime sup);

   @Query("SELECT AVG(o.rate) FROM UsersOffer as o WHERE o.id.offerId = ?1 AND o.rate IS NOT NULL")
    Float getAverageRateOffer(Long eventId);
}
