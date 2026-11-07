import type { Solicitud } from "../interfaces/Solicitud";

interface SolicitudDetalleModalProps {
    solicitud: Solicitud;
    onCerrar: () => void;
}

export default function SolicitudDetalleModal({
                                                  solicitud,
                                                  onCerrar
                                              }: SolicitudDetalleModalProps) {

    const formatearFecha = (
        fecha: string | null
    ) => {

        if (!fecha) {
            return "Sin registrar";
        }

        return new Date(fecha).toLocaleString(
            "es-PE",
            {
                day: "2-digit",
                month: "2-digit",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit"
            }
        );
    };

    return (
        <div
            className="modal-overlay"
            onClick={onCerrar}
        >
            <div
                className="modal-detalle"
                onClick={(event) =>
                    event.stopPropagation()
                }
            >

                <div className="modal-header">

                    <div>
                        <h2>
                            Detalle de solicitud #{solicitud.id}
                        </h2>

                        <p>
                            Información completa de la incidencia
                        </p>
                    </div>

                    <button
                        type="button"
                        className="modal-cerrar-x"
                        onClick={onCerrar}
                    >
                        ×
                    </button>

                </div>

                <div className="detalle-incidente">

                    <span className="detalle-label">
                        Incidente
                    </span>

                    <strong>
                        {solicitud.titulo}
                    </strong>

                </div>

                <div className="detalle-descripcion">

                    <span className="detalle-label">
                        Descripción
                    </span>

                    <p>
                        {solicitud.descripcion}
                    </p>

                </div>

                <div className="detalle-grid">

                    <div>
                        <span className="detalle-label">
                            Prioridad
                        </span>

                        <p>{solicitud.prioridad}</p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Gravedad
                        </span>

                        <p>{solicitud.gravedad}</p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Estado
                        </span>

                        <p>{solicitud.estado}</p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Cliente
                        </span>

                        <p>
                            {solicitud.clienteNombre}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Técnico
                        </span>

                        <p>
                            {solicitud.tecnicoNombre ??
                                "Sin asignar"}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Fecha de creación
                        </span>

                        <p>
                            {formatearFecha(
                                solicitud.fechaCreacion
                            )}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Fecha límite
                        </span>

                        <p>
                            {formatearFecha(
                                solicitud.fechaLimite
                            )}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Inicio de atención
                        </span>

                        <p>
                            {formatearFecha(
                                solicitud.fechaInicioAtencion
                            )}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Fecha de solución
                        </span>

                        <p>
                            {formatearFecha(
                                solicitud.fechaResolucion
                            )}
                        </p>
                    </div>

                    <div>
                        <span className="detalle-label">
                            Fecha de cierre
                        </span>

                        <p>
                            {formatearFecha(
                                solicitud.fechaCierre
                            )}
                        </p>
                    </div>

                </div>

                <div className="modal-actions">

                    <button
                        type="button"
                        className="btn-primary"
                        onClick={onCerrar}
                    >
                        Cerrar
                    </button>

                </div>

            </div>
        </div>
    );
}