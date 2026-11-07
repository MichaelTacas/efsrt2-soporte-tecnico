import type { Solicitud } from "../interfaces/Solicitud";
import { apiClient } from "./apiClient";

const API_URL = "/api/tecnico/solicitudes";

export const tecnicoSolicitudService = {

    listarMisSolicitudes(): Promise<Solicitud[]> {

        return apiClient.get<Solicitud[]>(
            API_URL
        );
    },

    iniciarAtencion(
        solicitudId: number
    ): Promise<Solicitud> {

        return apiClient.patch<Solicitud>(
            `${API_URL}/${solicitudId}/iniciar`
        );
    },

    resolverSolicitud(
        solicitudId: number
    ): Promise<Solicitud> {

        return apiClient.patch<Solicitud>(
            `${API_URL}/${solicitudId}/resolver`
        );
    }

};