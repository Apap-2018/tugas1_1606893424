package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;


@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}

	@Override
	public void ubahJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		
		JabatanModel updatedJabatan=jabatanDb.findById(jabatan.getId());
		
		
		updatedJabatan.setNama(jabatan.getNama());
		updatedJabatan.setDeskripsi(jabatan.getDeskripsi());
		updatedJabatan.setGaji_pokok(jabatan.getGaji_pokok());
		
	}

	@Override
	public void hapusJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.delete(jabatan);
		
	}

	@Override
	public List<JabatanModel> getListJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}
	
	
	
	


	
		
	

}
