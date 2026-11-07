import { useEffect, useState } from "react";

import SolicitudForm from "../components/SolicitudForm";
import SolicitudEstadoAction from "../components/SolicitudEstadoAction";
import SolicitudDetalleModal from "../components/SolicitudDetalleModal";

import type { Solicitud } from "../interfaces/Solicitud";
import type { SolicitudRequest } from "../interfaces/SolicitudRequest";

import { solicitudService } from "../services/solicitudService";
import { authService } from "../services/authService";

import "../styles/Solicitudes.css";

export default function SolicitudesPage() {

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
    // USUARIO AUTENTICADO Y ROL
    // =========================================

    const usuario =
        authService.obtenerUsuario();

    const esAdministrador =
        usuario?.rol === "ADMINISTRADOR";

    // =========================================
    // CARGAR SOLICITUDES
    // =========================================

    useEffect(() => {

        const cargarSolicitudes = async () => {

            try {

                setError("");

                const datos =
                    await solicitudService.listar();

                setSolicitudes(datos);

            } catch {

                setError(
                    "No se pudieron cargar las solicitudes"
                );

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
        solicitudRequest: SolicitudRequest
    ) => {

        const nuevaSolicitud =
            await solicitudService.registrar(
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
    // CAMBIAR ESTADO
    // =========================================

    const cambiarEstado = async (
        solicitud: Solicitud,
        nuevoEstado: Solicitud["estado"]
    ) => {

        try {

            setError("");

            const solicitudActualizada =
                await solicitudService.actualizar(
                    solicitud.id,
                    {
                        titulo:
                        solicitud.titulo,

                        descripcion:
                        solicitud.descripcion,

                        prioridad:
                        solicitud.prioridad,

                        gravedad:
                        solicitud.gravedad,

                        clienteId:
                        solicitud.clienteId,

                        tecnicoId:
                        solicitud.tecnicoId,

                        estado:
                        nuevoEstado
                    }
                );

            setSolicitudes(
                (solicitudesActuales) =>
                    solicitudesActuales.map(
                        (item) =>
                            item.id ===
                            solicitudActualizada.id
                                ? solicitudActualizada
                                : item
                    )
            );

            // Si el modal está abierto,
            // actualizamos también su información.
            if (
                solicitudSeleccionada?.id ===
                solicitudActualizada.id
            ) {
                setSolicitudSeleccionada(
                    solicitudActualizada
                );
            }

        } catch (error) {

            if (error instanceof Error) {

                setError(error.message);

            } else {

                setError(
                    "No se pudo actualizar el estado"
                );
            }
        }
    };

    // =========================================
    // VISTA
    // =========================================

    return (
        <main className="solicitudes-page">

            <header className="page-header">

                <div>

                    <h1>
                        Sistema de Soporte Técnico
                    </h1>

                    <p>
                        Gestión y seguimiento de
                        incidencias de soporte técnico.
                    </p>

                </div>

            </header>

            {/* =====================================
                FORMULARIO DEL ADMINISTRADOR
            ====================================== */}

            {esAdministrador && (

                <SolicitudForm
                    onRegistrar={
                        registrarSolicitud
                    }
                />

            )}

            {/* =====================================
                LISTADO DE SOLICITUDES
            ====================================== */}

            <section className="table-card">

                <div className="table-header">

                    <div>

                        <h2>
                            Solicitudes registradas
                        </h2>

                        <p>
                            Consulta y gestiona las
                            incidencias registradas.
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
                            No existen solicitudes
                            registradas.
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
                                        Cliente
                                    </th>

                                    <th>
                                        Técnico
                                    </th>

                                    <th>
                                        Prioridad
                                    </th>

                                    <th>
                                        Estado
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
                                                {
                                                    solicitud.clienteNombre
                                                }
                                            </td>

                                            <td>
                                                {
                                                    solicitud.tecnicoNombre ??
                                                    "Sin asignar"
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
                                                className={`estado estado-${solicitud.estado.toLowerCase()}`}
                                            >
                                                {
                                                    solicitud.estado
                                                }
                                            </span>

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

                                                    <SolicitudEstadoAction
                                                        solicitud={
                                                            solicitud
                                                        }
                                                        onCambiarEstado={
                                                            cambiarEstado
                                                        }
                                                    />

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
                DETALLE DE SOLICITUD
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