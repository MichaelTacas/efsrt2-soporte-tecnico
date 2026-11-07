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

    const mostrarFechaLimite = () => {

        if (!solicitud.fechaLimite) {

            if (!solicitud.gravedad) {
                return "Pendiente de clasificación";
            }

            return "Sin registrar";
        }

        return formatearFecha(
            solicitud.fechaLimite
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

                {/* ENCABEZADO */}

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

                {/* INCIDENTE */}

                <div className="detalle-incidente">

                    <span className="detalle-label">
                        Incidente
                    </span>

                    <strong>
                        {solicitud.titulo}
                    </strong>

                </div>

                {/* DESCRIPCIÓN */}

                <div className="detalle-descripcion">

                    <span className="detalle-label">
                        Descripción
                    </span>

                    <p>
                        {solicitud.descripcion}
                    </p>

                </div>

                {/* DATOS GENERALES */}

                <div className="detalle-grid">

                    <div>

                        <span className="detalle-label">
                            Prioridad
                        </span>

                        <p>
                            {solicitud.prioridad}
                        </p>

                    </div>

                    <div>

                        <span className="detalle-label">
                            Gravedad
                        </span>

                        <p>
                            {solicitud.gravedad ??
                                "Pendiente de clasificación"}
                        </p>

                    </div>

                    <div>

                        <span className="detalle-label">
                            Estado
                        </span>

                        <p>
                            {solicitud.estado}
                        </p>

                    </div>

                    {/* CLIENTE */}

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
                            Empresa
                        </span>

                        <p>
                            {solicitud.clienteEmpresa ||
                                "Sin registrar"}
                        </p>

                    </div>

                    <div>

                        <span className="detalle-label">
                            Área
                        </span>

                        <p>
                            {solicitud.clienteArea ||
                                "Sin registrar"}
                        </p>

                    </div>

                    <div>

                        <span className="detalle-label">
                            Teléfono
                        </span>

                        <p>
                            {solicitud.clienteTelefono ||
                                "Sin registrar"}
                        </p>

                    </div>

                    {/* TÉCNICO */}

                    <div>

                        <span className="detalle-label">
                            Técnico
                        </span>

                        <p>
                            {solicitud.tecnicoNombre ??
                                "Sin asignar"}
                        </p>

                    </div>

                    {/* FECHAS */}

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
                            {mostrarFechaLimite()}
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

                {/* ACCIONES */}

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