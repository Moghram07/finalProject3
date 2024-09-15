package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public void addSession(Session session) {
        session.setStatus("PENDING");
        sessionRepository.save(session);
    }

    public Session updateSession(Integer id, Session session) {
        Session s = sessionRepository.findSessionById(id);
        if (s == null) {
            throw new ApiException("Session not found");
        }
        s.setPrice(session.getPrice());
        s.setLearningMethod(session.getLearningMethod());
        s.setDuration(session.getDuration());
        return sessionRepository.save(s);
    }

    public void deleteSession(Integer id) {
        Session s = sessionRepository.findSessionById(id);
        if (s == null) {
            throw new ApiException("Session not found");
        }
        sessionRepository.deleteById(id);
    }

    public void startSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("PENDING".equals(session.getStatus())) {
            session.setStatus("ACTIVE");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be started. Current status: " + session.getStatus());
        }
    }

    public void cancelSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("ACTIVE".equals(session.getStatus()) || "PENDING".equals(session.getStatus())) {
            session.setStatus("CANCELED");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be canceled. Current status: " + session.getStatus());
        }
        //remove all students
        session.getStudents().clear();
        sessionRepository.save(session);
    }

    // end a session if it's "ACTIVE"
    public void endSession(Integer id) {
        Session session = sessionRepository.findSessionById(id);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if ("ACTIVE".equals(session.getStatus())) {
            session.setStatus("COMPLETED");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Only active sessions can be completed.");
        }
    }

    // Assign a student to a session if the maximum number of participants hasn't been reached
    public void assignStudentToSession(Integer sessionId, Integer studentId) {
        Session session = sessionRepository.findSessionById(sessionId);
        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new ApiException("Student not found");
        }
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (session.getStudents().size() >= session.getMaxParticipants()) {
            throw new ApiException("Maximum number of participants reached.");
        }
        session.getStudents().add(student);
        sessionRepository.save(session);
    }

    // Remove a student from a session (effectively blocking them from attending)
    public void blockStudentFromSession(Integer sessionId, Integer studentId) {
        Session session = sessionRepository.findSessionById(sessionId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiException("Student not found"));
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (session.getStudents().contains(student)) {
            session.getStudents().remove(student);
            sessionRepository.save(session);
        } else {
            throw new ApiException("The student is not part of this session.");
        }
    }

    public Set<Student> getStudentsInSession(Integer sessionId) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        return session.getStudents();
    }

    public List<Session> getSessionsByMaxParticipants(int maxParticipants) {
        return sessionRepository.findAllByMaxParticipants(maxParticipants);
    }
    // All Done by Omar
}
