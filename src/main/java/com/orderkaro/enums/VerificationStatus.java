package com.orderkaro.enums;

public enum VerificationStatus {

    PENDING,        // Uploaded, not yet reviewed
    IN_REVIEW,      // Ops team is verifying
    APPROVED,       // Verified & accepted
    REJECTED,       // Permanently rejected
    REUPLOAD_REQUIRED // Invalid / unclear, needs re-upload
}
