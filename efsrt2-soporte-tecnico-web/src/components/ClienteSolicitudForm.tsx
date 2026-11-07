import { useState } from "react";
import type { FormEvent } from "react";

import type { ClienteSolicitudRequest } from "../interfaces/ClienteSolicitudRequest";

interface ClienteSolicitudFormProps {
    onRegistrar: (
        solicitud: ClienteSolicitudRequest
    ) => Promise<void>;
}

function ClienteSolicitudForm({
                                  onRegistrar
                              }: ClienteSolicitudFormProps) {

    const [titulo, setTitulo] =
        useState("");

    const [descripcion, setDescripcion] =
        useState("");

    const [prioridad, setPrioridad] =
        useState<"BAJA" | "MEDIA" | "ALTA">(
            "MEDIA"
        );

    const [gravedad, setGravedad] =
        useState<
            "LEVE" |
            "MODERADA" |
            "GRAVE" |
            "CRITICA"
        >("MODERADA");

    const [enviando, setEnviando] =
        useState(false);

    const [mensaje, setMensaje] =
        useState("");

    // =========================================
    // REGISTRAR SOLICITUD
    // =========================================

    const handleSubmit = async (
        event: FormEvent<HTMLFormElement>
    ) => {

        event.preventDefault();

        const nuevaSolicitud:
            ClienteSolicitudRequest = {

            titulo,
            descripcion,
            prioridad,
            gravedad
        };

        try {

            setEnviando(true);
            setMensaje("");

            await onRegistrar(
                nuevaSolicitud
            );

            setTitulo("");
            setDescripcion("");
            setPrioridad("MEDIA");
            setGravedad("MODERADA");

            setMensaje(
                "Solicitud registrada correctamente"
            );

        } catch (error) {

            if (error instanceof Error) {

                setMensaje(
                    error.message
                );

            } else {

                setMensaje(
                    "No se pudo registrar la solicitud"
                );
            }

        } finally {

            setEnviando(false);
        }
    };

    return (
        <section className="form-card">

            <div className="form-header">

                <div>

                    <h2>
                        Nueva solicitud
                    </h2>

                    <p>
                        Registra una incidencia
                        para que el área de soporte
                        pueda atenderla.
                    </p>

                </div>

            </div>

            <form
                className="solicitud-form"
                onSubmit={handleSubmit}
            >

                {/* INCIDENTE */}

                <div className="form-group">

                    <label htmlFor="titulo">
                        Incidente
                    </label>

                    <input
                        id="titulo"
                        type="text"
                        value={titulo}
                        onChange={(event) =>
                            setTitulo(
                                event.target.value
                            )
                        }
                        placeholder="Ej. No puedo ingresar al sistema"
                        required
                    />

                </div>

                {/* PRIORIDAD */}

                <div className="form-group">

                    <label htmlFor="prioridad">
                        Prioridad
                    </label>

                    <select
                        id="prioridad"
                        value={prioridad}
                        onChange={(event) =>
                            setPrioridad(
                                event.target.value as
                                    | "BAJA"
                                    | "MEDIA"
                                    | "ALTA"
                            )
                        }
                    >

                        <option value="BAJA">
                            Baja
                        </option>

                        <option value="MEDIA">
                            Media
                        </option>

                        <option value="ALTA">
                            Alta
                        </option>

                    </select>

                </div>

                {/* DESCRIPCIÓN */}

                <div className="form-group form-group-full">

                    <label htmlFor="descripcion">
                        Descripción
                    </label>

                    <textarea
                        id="descripcion"
                        value={descripcion}
                        onChange={(event) =>
                            setDescripcion(
                                event.target.value
                            )
                        }
                        placeholder="Describe detalladamente el problema presentado..."
                        required
                    />

                </div>

                {/* GRAVEDAD */}

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
                            Leve
                        </option>

                        <option value="MODERADA">
                            Moderada
                        </option>

                        <option value="GRAVE">
                            Grave
                        </option>

                        <option value="CRITICA">
                            Crítica
                        </option>

                    </select>

                </div>

                {/* BOTÓN */}

                <div className="form-actions">

                    <button
                        className="btn-primary"
                        type="submit"
                        disabled={enviando}
                    >
                        {enviando
                            ? "Registrando..."
                            : "Registrar solicitud"}
                    </button>

                </div>

                {/* MENSAJE */}

                {mensaje && (

                    <p className="mensaje">
                        {mensaje}
                    </p>

                )}

            </form>

        </section>
    );
}

export default ClienteSolicitudForm;