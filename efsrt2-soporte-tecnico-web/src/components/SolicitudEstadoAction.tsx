import type { Solicitud } from "../interfaces/Solicitud";

interface SolicitudEstadoActionProps {
    solicitud: Solicitud;

    onCambiarEstado: (
        solicitud: Solicitud,
        nuevoEstado: Solicitud["estado"]
    ) => Promise<void>;
}

function SolicitudEstadoAction({
                                   solicitud,
                                   onCambiarEstado
                               }: SolicitudEstadoActionProps) {

    // =========================================
    // SOLICITUD CERRADA
    // =========================================

    if (solicitud.estado === "CERRADA") {
        return (
            <span className="sin-accion">
                Finalizada
            </span>
        );
    }

    // =========================================
    // SOLICITUD RESUELTA
    // EL ADMINISTRADOR PUEDE CERRARLA
    // =========================================

    if (solicitud.estado === "RESUELTA") {
        return (
            <button
                className="btn-estado"
                type="button"
                onClick={() =>
                    onCambiarEstado(
                        solicitud,
                        "CERRADA"
                    )
                }
            >
                Cerrar solicitud
            </button>
        );
    }

    // =========================================
    // SOLICITUD PENDIENTE
    // =========================================

    if (solicitud.estado === "PENDIENTE") {
        return (
            <span className="sin-accion">
                Pendiente de atención
            </span>
        );
    }

    // =========================================
    // SOLICITUD EN PROCESO
    // =========================================

    if (solicitud.estado === "EN_PROCESO") {
        return (
            <span className="sin-accion">
                En atención
            </span>
        );
    }

    // =========================================
    // ESTADO NO CONTEMPLADO
    // =========================================

    return (
        <span className="sin-accion">
            Sin acciones
        </span>
    );
}

export default SolicitudEstadoAction;