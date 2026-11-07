import { useEffect, useState } from "react";

import type { Solicitud } from "../interfaces/Solicitud";
import type { Tecnico } from "../interfaces/Tecnico";
import type { ClasificarSolicitudRequest } from "../interfaces/ClasificarSolicitudRequest";

import { tecnicoService } from "../services/tecnicoService";
import { adminSolicitudService } from "../services/adminSolicitudService";

interface ClasificarSolicitudModalProps {
    solicitud: Solicitud;

    onCerrar: () => void;

    onClasificada: (
        solicitud: Solicitud
    ) => void;
}

function ClasificarSolicitudModal({
                                      solicitud,
                                      onCerrar,
                                      onClasificada
                                  }: ClasificarSolicitudModalProps) {

    const [gravedad, setGravedad] =
        useState<
            | "LEVE"
            | "MODERADA"
            | "GRAVE"
            | "CRITICA"
        >("MODERADA");

    const [tecnicoId, setTecnicoId] =
        useState<number | null>(null);

    const [tecnicos, setTecnicos] =
        useState<Tecnico[]>([]);

    const [cargandoTecnicos, setCargandoTecnicos] =
        useState(true);

    const [guardando, setGuardando] =
        useState(false);

    const [mensaje, setMensaje] =
        useState("");

    // =========================================
    // CARGAR TÉCNICOS DISPONIBLES
    // =========================================

    useEffect(() => {

        const cargarTecnicos = async () => {

            try {

                setCargandoTecnicos(true);
                setMensaje("");

                const respuesta =
                    await tecnicoService.listarDisponibles();

                setTecnicos(respuesta);

            } catch (error) {

                if (error instanceof Error) {

                    setMensaje(error.message);

                } else {

                    setMensaje(
                        "No se pudieron cargar los técnicos disponibles."
                    );
                }

            } finally {

                setCargandoTecnicos(false);
            }
        };

        cargarTecnicos();

    }, []);

    // =========================================
    // GUARDAR CLASIFICACIÓN
    // =========================================

    const guardarClasificacion = async () => {

        setMensaje("");

        if (tecnicoId === null) {

            setMensaje(
                "Debes seleccionar un técnico."
            );

            return;
        }

        const datos: ClasificarSolicitudRequest = {
            gravedad,
            tecnicoId
        };

        try {

            setGuardando(true);

            const solicitudActualizada =
                await adminSolicitudService.clasificar(
                    solicitud.id,
                    datos
                );

            onClasificada(
                solicitudActualizada
            );

            onCerrar();

        } catch (error) {

            if (error instanceof Error) {

                setMensaje(
                    error.message
                );

            } else {

                setMensaje(
                    "No se pudo clasificar la solicitud."
                );
            }

        } finally {

            setGuardando(false);
        }
    };

    return (
        <div
            className="modal-overlay"
            onClick={onCerrar}
        >

            <div
                className="modal-contenido"
                onClick={(event) =>
                    event.stopPropagation()
                }
            >

                {/* =====================================
                    ENCABEZADO
                ====================================== */}

                <div className="modal-header">

                    <div>

                        <h2>
                            Clasificar solicitud
                        </h2>

                        <p>
                            Solicitud #{solicitud.id}
                        </p>

                    </div>

                    <button
                        type="button"
                        className="modal-cerrar"
                        onClick={onCerrar}
                    >
                        ×
                    </button>

                </div>

                <div className="modal-body">

                    {/* =====================================
                        INFORMACIÓN DE LA SOLICITUD
                    ====================================== */}

                    <div className="clasificacion-info">

                        <div className="clasificacion-incidente">

                            <span className="detalle-label">
                                Incidente
                            </span>

                            <strong>
                                {solicitud.titulo}
                            </strong>

                        </div>

                        <div className="clasificacion-info-grid">

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
                                    Área
                                </span>

                                <p>
                                    {solicitud.clienteArea ||
                                        "Sin registrar"}
                                </p>

                            </div>

                            <div>

                                <span className="detalle-label">
                                    Prioridad reportada
                                </span>

                                <p>
                                    {solicitud.prioridad}
                                </p>

                            </div>

                        </div>

                        <div className="clasificacion-descripcion">

                            <span className="detalle-label">
                                Descripción
                            </span>

                            <p>
                                {solicitud.descripcion}
                            </p>

                        </div>

                    </div>

                    {/* =====================================
                        GRAVEDAD
                    ====================================== */}

                    <div className="form-group">

                        <label htmlFor="gravedad">
                            Gravedad
                        </label>

                        <select
                            id="gravedad"
                            value={gravedad}
                            onChange={(event) =>
                                setGravedad(
                                    event.target.value as
                                        | "LEVE"
                                        | "MODERADA"
                                        | "GRAVE"
                                        | "CRITICA"
                                )
                            }
                        >

                            <option value="LEVE">
                                Leve - 48 horas
                            </option>

                            <option value="MODERADA">
                                Moderada - 24 horas
                            </option>

                            <option value="GRAVE">
                                Grave - 12 horas
                            </option>

                            <option value="CRITICA">
                                Crítica - 4 horas
                            </option>

                        </select>

                    </div>

                    {/* =====================================
                        TÉCNICO
                    ====================================== */}

                    <div className="form-group">

                        <label htmlFor="tecnico">
                            Técnico
                        </label>

                        <select
                            id="tecnico"
                            value={
                                tecnicoId ?? ""
                            }
                            onChange={(event) =>
                                setTecnicoId(
                                    event.target.value
                                        ? Number(
                                            event.target.value
                                        )
                                        : null
                                )
                            }
                            disabled={
                                cargandoTecnicos
                            }
                        >

                            <option value="">

                                {cargandoTecnicos
                                    ? "Cargando técnicos..."
                                    : "Selecciona un técnico"}

                            </option>

                            {tecnicos.map(
                                (tecnico) => (

                                    <option
                                        key={tecnico.id}
                                        value={tecnico.id}
                                    >
                                        {tecnico.nombre}
                                    </option>

                                )
                            )}

                        </select>

                    </div>

                    {/* =====================================
                        MENSAJE
                    ====================================== */}

                    {mensaje && (

                        <p className="mensaje-error">
                            {mensaje}
                        </p>

                    )}

                </div>

                {/* =====================================
                    ACCIONES
                ====================================== */}

                <div className="modal-actions">

                    <button
                        type="button"
                        className="btn-link"
                        onClick={onCerrar}
                        disabled={guardando}
                    >
                        Cancelar
                    </button>

                    <button
                        type="button"
                        className="btn-primary"
                        onClick={
                            guardarClasificacion
                        }
                        disabled={
                            guardando ||
                            cargandoTecnicos
                        }
                    >
                        {guardando
                            ? "Guardando..."
                            : "Guardar clasificación"}
                    </button>

                </div>

            </div>

        </div>
    );
}

export default ClasificarSolicitudModal;