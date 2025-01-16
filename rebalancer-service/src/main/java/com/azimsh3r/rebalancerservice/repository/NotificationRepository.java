package com.azimsh3r.rebalancerservice.repository;

import com.azimsh3r.rebalancerservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.status = 'PENDING' OR n.status = 'FAILED'")
    List<Notification> findUndeliveredNotifications();
}
