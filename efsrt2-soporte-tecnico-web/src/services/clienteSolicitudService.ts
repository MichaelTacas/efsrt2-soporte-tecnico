import type { Solicitud } from "../interfaces/Solicitud";
import type { ClienteSolicitudRequest } from "../interfaces/ClienteSolicitudRequest";

import { apiClient } from "./apiClient";

const API_URL = "/api/cliente/solicitudes";

export const clienteSolicitudService = {

    listarMisSolicitudes(): Promise<Solicitud[]> {

        return apiClient.get<Solicitud[]>(
            API_URL
        );
    },

    registrar(
        solicitud: ClienteSolicitudRequest
    ): Promise<Solicitud> {

        return apiClient.post<Solicitud>(
            API_URL,
            solicitud
        );
    }

};