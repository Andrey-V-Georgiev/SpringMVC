package org.softuni.residentevil.repositories;

import org.softuni.residentevil.domain.entities.Virus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirusRepository extends JpaRepository<Virus, String> {
}
