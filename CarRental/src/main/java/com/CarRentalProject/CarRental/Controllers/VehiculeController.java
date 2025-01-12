package com.CarRentalProject.CarRental.Controllers;

import com.CarRentalProject.CarRental.Enums.Status_Voiture;
import com.CarRentalProject.CarRental.Models.Caracteristique_voiture;
import com.CarRentalProject.CarRental.Models.Vehicule;
import com.CarRentalProject.CarRental.Services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @Autowired
    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }
    // dsdsadsa

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addVehicule(
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam("type") String type,
            @RequestParam("tarif_de_location") Integer tarifDeLocation,
            @RequestParam("status") Status_Voiture status,
            @RequestParam(value = "caracteristique", required = false) Set<Caracteristique_voiture> caracteristique,
            @RequestParam("imageVoiture") MultipartFile imageVoiture) {
        try {
            // Save the file
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = uploadDir + imageVoiture.getOriginalFilename();
            imageVoiture.transferTo(new File(filePath));

            // Save the vehicle details
            Vehicule vehicule = new Vehicule();
            vehicule.setMarque(marque);
            vehicule.setModel(model);
            vehicule.setType(type);
            vehicule.setTarif_de_location(tarifDeLocation);
            vehicule.setStatus_voiture(status);
            vehicule.setCaracteristique(caracteristique);
            vehicule.setImageVoiture(filePath); // Save file path
            try {
                vehiculeService.addVehicule(vehicule);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }

            return ResponseEntity.ok("Vehicle added successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    // not working
    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable Integer id) {
        return vehiculeService.getVehiculeById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin(origins = "http://localhost:5173")
    public void deleteVehicule(@PathVariable int id) {
        vehiculeService.deleteVehicule(id);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> updateVehicule(
            @PathVariable("id") Integer id,
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam("type") String type,
            @RequestParam("tarif_de_location") Integer tarifDeLocation,
            @RequestParam("status") Status_Voiture status,
            @RequestParam(value = "caracteristique", required = false) Set<Caracteristique_voiture> caracteristique,
            @RequestParam(value = "imageVoiture", required = false) MultipartFile imageVoiture) {
        try {
            Vehicule vehicule = vehiculeService.getVehiculeById(id);
            if (vehicule == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found.");
            }

            vehicule.setMarque(marque);
            vehicule.setModel(model);
            vehicule.setType(type);
            vehicule.setTarif_de_location(tarifDeLocation);
            vehicule.setStatus_voiture(status);
            vehicule.setCaracteristique(caracteristique);

            if (imageVoiture != null && !imageVoiture.isEmpty()) {
                // Save the new file
                String uploadDir = "uploads/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String filePath = uploadDir + imageVoiture.getOriginalFilename();
                imageVoiture.transferTo(new File(filePath));
                vehicule.setImageVoiture(filePath); // Update file path
            }

            vehiculeService.updateVehicule(vehicule);
            return ResponseEntity.ok("Vehicle updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/modelBymarque/{marque}")
    public List<String> getModelByMarque(@PathVariable("marque") String marque) {
        return vehiculeService.getModelByMarque(marque);
    }

    @GetMapping("/marques")
    public List<String> getMarqueOfCars() {
        return vehiculeService.getMarque();
    }

    @GetMapping("/models/{marque}")
    public List<String> getModelsOf(@PathVariable("marque") String marque) {
        return vehiculeService.getModelOfMarque(marque);
    }

    @GetMapping("/tarif/{tarifmin}-{tarifmax}")
    public List<Vehicule> getVehiculeByTarifBetween(@PathVariable("tarifmin") int tarifmin,
            @PathVariable("tarifmax") int tarifmax) {
        return vehiculeService.getVehiculeByTarifBetween(tarifmin, tarifmax);
    }

    @GetMapping("/marque/{marque}")
    public List<Vehicule> getVehiculeByMarque(@PathVariable("marque") String marque) {
        return vehiculeService.getVehiculeByMarque(marque);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Define the directory to save the uploaded file
        String uploadDir = "uploads/";

        try {
            // Create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file to the directory
            file.transferTo(new File(uploadDir + file.getOriginalFilename()));

            return ResponseEntity.status(HttpStatus.OK)
                    .body("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Adjust the content type based on your image type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
