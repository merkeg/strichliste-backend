package de.merkeg.strichlistebackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Strichliste Backend",
                description = "A revamped backend for the Strichliste @ H.O.M.E.",
                contact = @Contact(
                        name = "Egor Merk",
                        email = "hello@merkeg.de",
                        url = "https://merkeg.de"
                )
        )
)
public class OpenAPIConfiguration {
}
