import type { ClasificarSolicitudRequest }
    from "../interfaces/ClasificarSolicitudRequest";

import type { Solicitud }
    from "../interfaces/Solicitud";

import { apiClient }
    from "./apiClient";

const API_URL =
    "/api/admin/solicitudes";

export const adminSolicitudService = {

    clasificar(
        solicitudId: number,
        datos: ClasificarSolicitudRequest
    ): Promise<Solicitud> {

        return apiClient.patch(
            `${API_URL}/${solicitudId}/clasificar`,
            datos
        );
    }
};