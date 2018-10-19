package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;


public interface JabatanService {
	
	void addJabatan(JabatanModel jabatan);
	
	JabatanModel getJabatanDetailById(long idJabatan);

	void ubahJabatan(JabatanModel jabatan);
	
	void hapusJabatan(JabatanModel jabatan);
	
	List<JabatanModel> getListJabatan();

}
