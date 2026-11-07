import { useEffect, useState } from "react";

import type { Solicitud } from "../interfaces/Solicitud";

import { tecnicoSolicitudService } from "../services/tecnicoSolicitudService";

import SolicitudDetalleModal from "../components/SolicitudDetalleModal";

import "../styles/Solicitudes.css";

export default function TecnicoSolicitudesPage() {

    const [solicitudes, setSolicitudes] =
        useState<Solicitud[]>([]);

    const [cargando, setCargando] =
        useState(true);

    const [error, setError] =
        useState("");

    const [procesandoId, setProcesandoId] =
        useState<number | null>(null);

    const [
        solicitudSeleccionada,
        setSolicitudSeleccionada
    ] = useState<Solicitud | null>(null);

    // =========================================
    // FORMATEAR FECHA
    // =========================================

    const formatearFecha = (
        fecha: string | null
    ) => {

        if (!fecha) {
            return "-";
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

    // =========================================
    // CARGAR SOLICITUDES DEL TÉCNICO
    // =========================================

    useEffect(() => {

        const cargarSolicitudes = async () => {

            try {

                setError("");

                const datos =
                    await tecnicoSolicitudService
                        .listarMisSolicitudes();

                setSolicitudes(datos);

            } catch (error) {

                if (error instanceof Error) {

                    setError(error.message);

                } else {

                    setError(
                        "No se pudieron cargar las solicitudes"
                    );
                }

            } finally {

                setCargando(false);
            }
        };

        cargarSolicitudes();

    }, []);

    // =========================================
    // ACTUALIZAR SOLICITUD LOCAL
    // =========================================

    const actualizarSolicitudLocal = (
        solicitudActualizada: Solicitud
    ) => {

        setSolicitudes(
            (solicitudesActuales) =>
                solicitudesActuales.map(
                    (solicitud) =>
                        solicitud.id ===
                        solicitudActualizada.id
                            ? solicitudActualizada
                            : solicitud
                )
        );

        // Si el modal está abierto,
        // también actualizamos sus datos.
        if (
            solicitudSeleccionada?.id ===
            solicitudActualizada.id
        ) {

            setSolicitudSeleccionada(
                solicitudActualizada
            );
        }
    };

    // =========================================
    // INICIAR ATENCIÓN
    // =========================================

    const iniciarAtencion = async (
        solicitudId: number
    ) => {

        try {

            setError("");
            setProcesandoId(solicitudId);

            const solicitudActualizada =
                await tecnicoSolicitudService
                    .iniciarAtencion(
                        solicitudId
                    );

            actualizarSolicitudLocal(
                solicitudActualizada
            );

        } catch (error) {

            if (error instanceof Error) {

                setError(error.message);

            } else {

                setError(
                    "No se pudo iniciar la atención"
                );
            }

        } finally {

            setProcesandoId(null);
        }
    };

    // =========================================
    // RESOLVER SOLICITUD
    // =========================================

    const resolverSolicitud = async (
        solicitudId: number
    ) => {

        try {

            setError("");
            setProcesandoId(solicitudId);

            const solicitudActualizada =
                await tecnicoSolicitudService
                    .resolverSolicitud(
                        solicitudId
                    );

            actualizarSolicitudLocal(
                solicitudActualizada
            );

        } catch (error) {

            if (error instanceof Error) {

                setError(error.message);

            } else {

                setError(
                    "No se pudo resolver la solicitud"
                );
            }

        } finally {

            setProcesandoId(null);
        }
    };

    // =========================================
    // ACCIÓN SEGÚN ESTADO
    // =========================================

    const renderAccion = (
        solicitud: Solicitud
    ) => {

        const procesando =
            procesandoId === solicitud.id;

        if (solicitud.estado === "PENDIENTE") {

            return (
                <button
                    className="btn-estado"
                    type="button"
                    disabled={procesando}
                    onClick={() =>
                        iniciarAtencion(
                            solicitud.id
                        )
                    }
                >
                    {procesando
                        ? "Iniciando..."
                        : "Iniciar atención"}
                </button>
            );
        }

        if (
            solicitud.estado ===
            "EN_PROCESO"
        ) {

            return (
                <button
                    className="btn-estado"
                    type="button"
                    disabled={procesando}
                    onClick={() =>
                        resolverSolicitud(
                            solicitud.id
                        )
                    }
                >
                    {procesando
                        ? "Procesando..."
                        : "Marcar resuelta"}
                </button>
            );
        }

        if (
            solicitud.estado ===
            "RESUELTA"
        ) {

            return (
                <span className="sin-accion">
                    Pendiente de cierre
                </span>
            );
        }

        if (
            solicitud.estado ===
            "CERRADA"
        ) {

            return (
                <span className="sin-accion">
                    Finalizada
                </span>
            );
        }

        return (
            <span className="sin-accion">
                Sin acciones
            </span>
        );
    };

    // =========================================
    // VISTA
    // =========================================

    return (
        <main className="solicitudes-page">

            <header className="page-header">

                <div>

                    <h1>
                        Mis incidencias asignadas
                    </h1>

                    <p>
                        Gestiona las solicitudes de
                        soporte técnico asignadas a tu
                        cuenta.
                    </p>

                </div>

            </header>

            <section className="table-card">

                <div className="table-header">

                    <div>

                        <h2>
                            Solicitudes asignadas
                        </h2>

                        <p>
                            Revisa las incidencias,
                            su prioridad y fecha límite.
                        </p>

                    </div>

                </div>

                {cargando && (
                    <p>
                        Cargando solicitudes...
                    </p>
                )}

                {error && (
                    <p className="mensaje-error">
                        {error}
                    </p>
                )}

                {!cargando &&
                    !error &&
                    solicitudes.length === 0 && (

                        <p>
                            No tienes solicitudes
                            asignadas.
                        </p>

                    )}

                {!cargando &&
                    !error &&
                    solicitudes.length > 0 && (

                        <div className="table-container">

                            <table className="solicitudes-table">

                                <thead>

                                <tr>

                                    <th>ID</th>

                                    <th>
                                        Incidente
                                    </th>

                                    <th>
                                        Cliente
                                    </th>

                                    <th>
                                        Prioridad
                                    </th>

                                    <th>
                                        Gravedad
                                    </th>

                                    <th>
                                        Estado
                                    </th>

                                    <th>
                                        Fecha límite
                                    </th>

                                    <th>
                                        Acciones
                                    </th>

                                </tr>

                                </thead>

                                <tbody>

                                {solicitudes.map(
                                    (solicitud) => (

                                        <tr
                                            key={
                                                solicitud.id
                                            }
                                        >

                                            <td>
                                                {solicitud.id}
                                            </td>

                                            <td>

                                                <strong>
                                                    {
                                                        solicitud.titulo
                                                    }
                                                </strong>

                                            </td>

                                            <td>
                                                {
                                                    solicitud.clienteNombre
                                                }
                                            </td>

                                            <td>

                                            <span
                                                className={`prioridad prioridad-${solicitud.prioridad.toLowerCase()}`}
                                            >
                                                {
                                                    solicitud.prioridad
                                                }
                                            </span>

                                            </td>

                                            <td>

                                            <span
                                                className={`gravedad gravedad-${solicitud.gravedad.toLowerCase()}`}
                                            >
                                                {
                                                    solicitud.gravedad
                                                }
                                            </span>

                                            </td>

                                            <td>

                                            <span
                                                className={`estado estado-${solicitud.estado.toLowerCase()}`}
                                            >
                                                {
                                                    solicitud.estado
                                                }
                                            </span>

                                            </td>

                                            <td>
                                                {
                                                    formatearFecha(
                                                        solicitud.fechaLimite
                                                    )
                                                }
                                            </td>

                                            <td>

                                                <div className="acciones-solicitud">

                                                    <button
                                                        type="button"
                                                        className="btn-detalle"
                                                        onClick={() =>
                                                            setSolicitudSeleccionada(
                                                                solicitud
                                                            )
                                                        }
                                                    >
                                                        Ver detalle
                                                    </button>

                                                    {renderAccion(
                                                        solicitud
                                                    )}

                                                </div>

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

                        </div>

                    )}

            </section>

            {/* =====================================
                DETALLE DEL TICKET
            ====================================== */}

            {solicitudSeleccionada && (

                <SolicitudDetalleModal
                    solicitud={
                        solicitudSeleccionada
                    }
                    onCerrar={() =>
                        setSolicitudSeleccionada(
                            null
                        )
                    }
                />

            )}

        </main>
    );
}