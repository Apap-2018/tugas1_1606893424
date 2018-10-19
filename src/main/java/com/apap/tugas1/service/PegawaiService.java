package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	
	void addPegawai(PegawaiModel pegawai);
	
	List<PegawaiModel> getListPegawai();
	
	void hapusPegawai(PegawaiModel pegawai);
	
	PegawaiModel getPegawaiDetailById(long id);
	
	void ubahPegawai(PegawaiModel pegawai);
	
	int calculateGaji (String nip);
	
	PegawaiModel cariPegawaiTermuda(List<PegawaiModel> listPegawaiInstansi);
	PegawaiModel cariPegawaiTertua(List<PegawaiModel> listPegawaiInstansi);
	
	List<PegawaiModel> getPegawaiDetailByIdInstansi(String instansiId);
	
	

}
