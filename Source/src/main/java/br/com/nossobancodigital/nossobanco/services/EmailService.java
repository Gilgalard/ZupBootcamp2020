package br.com.nossobancodigital.nossobanco.services;

public interface EmailService {
    Boolean sendSimpleMessage(String to, String subject, String text);
}
