package com.example.product_service.service;

import com.example.product_service.Dto.BatchDto;
import com.example.product_service.Dto.BatchResponse;
import com.example.product_service.entity.Batch;
import com.example.product_service.entity.Course;
import com.example.product_service.repository.BatchRepo;
import com.example.product_service.repository.CourseRepo;
import com.example.product_service.repository.TenantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BatchService {
    private final BatchRepo batchRepo;
    private final CourseRepo courseRepo;
    private final TenantRepository tenantRepo;

    public BatchService(BatchRepo batchRepo, CourseRepo courseRepo, TenantRepository tenantRepo) {
        this.batchRepo = batchRepo;
        this.courseRepo = courseRepo;
        this.tenantRepo = tenantRepo;
    }

    private Batch mapToEntity(BatchDto dto,Course course){
        Batch batch = new Batch();
        batch.setBatchName(dto.getBatchName());
        batch.setCourse(course);
        batch.setSection(dto.getSection());
        batch.setTenant(null);
        batch.setTotalStudent(dto.getTotalStudent());
        batch.setYear(dto.getYear());
        return batch;
    }

    public Batch createBatch(BatchDto dto){
         tenantRepo.findById(dto.getTenantId()).orElseThrow(()-> new RuntimeException("tenant not found!"));
        Course course = courseRepo.findById(dto.getCourseId()).orElseThrow(()-> new RuntimeException("course not found!"));
        Batch batch = mapToEntity(dto,course);

        return batchRepo.save(batch);
    }

    public List<Batch> getAllBatches(){
        return batchRepo.findAll();
    }

    private BatchResponse mapToResponse(Batch batch){
        return new BatchResponse(
                batch.getId(),
                batch.getBatchName(),
                batch.getCourse().getCourseName(),
                batch.getSection(),
                batch.getYear(),
                batch.getTotalStudent()
        );
    }

    public Batch getBatchById(Long batchId){
        return batchRepo.findById(batchId).orElseThrow(()-> new RuntimeException("failed to get Batch!"));
    }
}
