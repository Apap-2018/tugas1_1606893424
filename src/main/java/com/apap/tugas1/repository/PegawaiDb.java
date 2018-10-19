package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findByNip(String nip);
	PegawaiModel findById (long id);
	
	List<PegawaiModel> findByInstansiId (long instansi);
	
	//PegawaiModel findByIdAndNipAnd
	

}
