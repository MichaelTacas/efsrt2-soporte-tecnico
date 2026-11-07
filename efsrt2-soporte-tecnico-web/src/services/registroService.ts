import type { RegistroClienteRequest } from "../interfaces/RegistroClienteRequest";
import { apiClient } from "./apiClient";

const API_URL = "/api/auth/registro";

export const registroService = {

    registrar(
        datos: RegistroClienteRequest
    ): Promise<unknown> {

        return apiClient.post(
            API_URL,
            datos
        );
    }

};