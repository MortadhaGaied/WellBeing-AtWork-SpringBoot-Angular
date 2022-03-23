package com.wellbeignatwork.backend.service;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.wellbeignatwork.backend.entity.Departement;
import com.wellbeignatwork.backend.entity.Event;
import com.wellbeignatwork.backend.entity.Subscription;
import com.wellbeignatwork.backend.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IActivityService {
    public void addEvent(Event e);
    public void deleteEvent(Event e);
    public Event updateEvent(Event e);
    public List<Event> getAllEvents();
    public void addUser(User u);
    public void assignUserToEvent (Long idUser, Long idEvent);
    public void export(HttpServletResponse response, Long idEvent, Long idUser, String text, int width, int height, String filePath) throws DocumentException, IOException, WriterException;
    //public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException;
    public int getNbrOfParticipant(Long idEvenet);
    public double calculDistance (String a, String b);
    public List<Event> sortedByDistance();
    public List<Event> sortedByFrais();
    //public List<ITagsCount> tags();
    public double getRevenueByEvent(Long idEvent);
    public Set<Event> showEventsByUser (Long idUser);
    public void reductionEvent (Long idEvent, Long idUser, int familyNumber);
    public Event popularEvent();
    public void assignPointToUser (Long idUser, Long idEvent);
     //public void validerEvent(Long idUser, Long idEvent);
    public Set<Event> filtreByDepartement(Departement departement);
     public void addSubscription(Subscription s);
     public void deleteSubscription(Subscription s);
     public void updateSubscription(Subscription s);
     public List<Subscription> getAllSubscriptions();
     public void assignUserToSubscription (Long idUser, Long idSubscription);
}
