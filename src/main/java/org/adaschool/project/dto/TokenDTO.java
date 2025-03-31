package org.adaschool.project.dto;

import java.util.Date;

public record TokenDTO (
        String token,
        Date expirationDate) {
}