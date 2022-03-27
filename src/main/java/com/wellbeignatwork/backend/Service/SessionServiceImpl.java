package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.SessionRepository;
import com.wellbeignatwork.backend.ServiceImp.SessionService;
import com.wellbeignatwork.backend.model.Session;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        super();
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session findSessionById(Long id) {
        return sessionRepository.findByid(id);
    }
}
