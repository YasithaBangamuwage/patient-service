package com.yas.patientservice.controller

import com.yas.patientservice.dto.PatientRequest
import com.yas.patientservice.dto.PatientResponse
import com.yas.patientservice.model.Patient
import com.yas.patientservice.service.PatientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/patients")
class PatientController(private val patientService: PatientService) {

    @PostMapping
    suspend fun createPatient(@RequestBody patientRequest: PatientRequest): PatientResponse =
        patientService.savePatient(patientRequest.toModel())?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)

    @GetMapping
    suspend fun findAll(@RequestParam("name", required = true) name: String?): Flow<PatientResponse> {
        val patients = name?.let { patientService.findByNameLike(it) } ?: patientService.findAllPatients()
        return patients.map { it.toResponse() }
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: Long): PatientResponse =
        patientService.findById(id)?.let { it.toResponse() } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @DeleteMapping("/{id}")
    suspend fun deleteById(@PathVariable id: Long) = patientService.deleteById(id)

    @PutMapping("/{id}")
    suspend fun updateById(@PathVariable id: Long, @RequestBody patientRequest: PatientRequest): PatientResponse =
        patientService.updateById(id, patientRequest.toModel()).toResponse()

    fun PatientRequest.toModel(): Patient =
        Patient(
            name = this.name,
            age = this.age,
            gender = this.gender
        )

    fun Patient.toResponse(): PatientResponse =
        PatientResponse(
            id = this.id!!,
            name = this.name,
            age = this.age,
            gender = this.gender
        )
}