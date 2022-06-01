package com.example.ankieter.service;

import com.example.ankieter.repository.FormRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormService implements IFormService {

  @Autowired
  private FormRepository formRepository;

}