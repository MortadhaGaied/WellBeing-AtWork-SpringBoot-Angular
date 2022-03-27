package com.wellbeignatwork.backend.ServiceImp;

import com.wellbeignatwork.backend.model.Session;

import java.util.List;

public interface SessionService {
	List<Session> getAllSessions();
	Session saveSession(Session session);
	Session findSessionById(Long id);
	
}
