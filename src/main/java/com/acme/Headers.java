package com.acme;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Headers(@JsonProperty("Accept") String accept) {
}
