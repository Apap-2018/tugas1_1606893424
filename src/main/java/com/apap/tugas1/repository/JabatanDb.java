package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;


public interface JabatanDb extends JpaRepository<JabatanModel, Long> {
	JabatanModel findById (long id);

}
