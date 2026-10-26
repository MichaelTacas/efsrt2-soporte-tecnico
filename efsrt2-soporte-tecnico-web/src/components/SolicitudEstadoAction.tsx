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

    const obtenerSiguienteEstado = (): Solicitud["estado"] | null => {

        switch (solicitud.estado) {

            case "PENDIENTE":
                return "EN_PROCESO";

            case "EN_PROCESO":
                return "RESUELTA";

            case "RESUELTA":
                return "CERRADA";

            case "CERRADA":
                return null;
        }
    };

    const obtenerTextoBoton = () => {

        switch (solicitud.estado) {

            case "PENDIENTE":
                return "Iniciar atención";

            case "EN_PROCESO":
                return "Marcar resuelta";

            case "RESUELTA":
                return "Cerrar";

            case "CERRADA":
                return "";
        }
    };

    const siguienteEstado = obtenerSiguienteEstado();

    if (siguienteEstado === null) {
        return (
            <span className="sin-accion">
        Finalizada
      </span>
        );
    }

    return (
        <button
            className="btn-estado"
            type="button"
            onClick={() =>
                onCambiarEstado(
                    solicitud,
                    siguienteEstado
                )
            }
        >
            {obtenerTextoBoton()}
        </button>
    );
}

export default SolicitudEstadoAction;