import { useEffect, useState } from "react";

import ClienteSolicitudForm from "../components/ClienteSolicitudForm";
import SolicitudDetalleModal from "../components/SolicitudDetalleModal";

import type { Solicitud } from "../interfaces/Solicitud";
import type { ClienteSolicitudRequest } from "../interfaces/ClienteSolicitudRequest";

import { clienteSolicitudService } from "../services/clienteSolicitudService";

import "../styles/Solicitudes.css";

export default function ClienteSolicitudesPage() {

    const [solicitudes, setSolicitudes] =
        useState<Solicitud[]>([]);

    const [cargando, setCargando] =
        useState(true);

    const [error, setError] =
        useState("");

    const [
        solicitudSeleccionada,
        setSolicitudSeleccionada
    ] = useState<Solicitud | null>(null);

    // =========================================
    // FORMATEAR FECHAS
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
    // CARGAR MIS SOLICITUDES
    // =========================================

    useEffect(() => {

        const cargarSolicitudes = async () => {

            try {

                setError("");

                const datos =
                    await clienteSolicitudService
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
    // REGISTRAR SOLICITUD
    // =========================================

    const registrarSolicitud = async (
        solicitudRequest: ClienteSolicitudRequest
    ) => {

        const nuevaSolicitud =
            await clienteSolicitudService
                .registrar(
                    solicitudRequest
                );

        setSolicitudes(
            (solicitudesActuales) => [
                nuevaSolicitud,
                ...solicitudesActuales
            ]
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
                        Portal de Soporte
                    </h1>

                    <p>
                        Registra y realiza seguimiento
                        a tus solicitudes de soporte.
                    </p>

                </div>

            </header>

            {/* =====================================
                NUEVA SOLICITUD
            ====================================== */}

            <ClienteSolicitudForm
                onRegistrar={
                    registrarSolicitud
                }
            />

            {/* =====================================
                MIS SOLICITUDES
            ====================================== */}

            <section className="table-card">

                <div className="table-header">

                    <div>

                        <h2>
                            Mis solicitudes
                        </h2>

                        <p>
                            Consulta el estado y avance
                            de tus incidencias.
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
                            No tienes solicitudes registradas.
                        </p>

                    )}

                {!cargando &&
                    !error &&
                    solicitudes.length > 0 && (

                        <div className="table-container">

                            <table className="solicitudes-table">

                                <thead>

                                <tr>

                                    <th>
                                        ID
                                    </th>

                                    <th>
                                        Incidente
                                    </th>

                                    <th>
                                        Prioridad
                                    </th>

                                    <th>
                                        Estado
                                    </th>

                                    <th>
                                        Técnico
                                    </th>

                                    <th>
                                        Creación
                                    </th>

                                    <th>
                                        Acción
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
                                                {
                                                    solicitud.id
                                                }
                                            </td>

                                            <td>
                                                <strong>
                                                    {
                                                        solicitud.titulo
                                                    }
                                                </strong>
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
                                                className={`estado estado-${solicitud.estado.toLowerCase()}`}
                                            >
                                                {
                                                    solicitud.estado
                                                }
                                            </span>

                                            </td>

                                            <td>
                                                {
                                                    solicitud.tecnicoNombre ??
                                                    "Sin asignar"
                                                }
                                            </td>

                                            <td>
                                                {
                                                    formatearFecha(
                                                        solicitud.fechaCreacion
                                                    )
                                                }
                                            </td>

                                            <td>

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

                                            </td>

                                        </tr>

                                    ))}

                                </tbody>

                            </table>

                        </div>

                    )}

            </section>

            {/* =====================================
                MODAL DETALLE
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