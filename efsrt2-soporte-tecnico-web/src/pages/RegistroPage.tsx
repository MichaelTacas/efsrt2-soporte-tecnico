import { useState } from "react";
import type { FormEvent } from "react";

import type { RegistroClienteRequest } from "../interfaces/RegistroClienteRequest";

import { registroService } from "../services/registroService";

interface RegistroPageProps {
    onVolverLogin: () => void;
}

export default function RegistroPage({
                                         onVolverLogin
                                     }: RegistroPageProps) {

    const [nombre, setNombre] =
        useState("");

    const [email, setEmail] =
        useState("");

    const [empresa, setEmpresa] =
        useState("");

    const [password, setPassword] =
        useState("");

    const [confirmarPassword, setConfirmarPassword] =
        useState("");

    const [enviando, setEnviando] =
        useState(false);

    const [mensaje, setMensaje] =
        useState("");

    const [registroExitoso, setRegistroExitoso] =
        useState(false);

    // =========================================
    // REGISTRAR CLIENTE
    // =========================================

    const handleSubmit = async (
        event: FormEvent<HTMLFormElement>
    ) => {

        event.preventDefault();

        setMensaje("");

        if (password !== confirmarPassword) {

            setMensaje(
                "Las contraseñas no coinciden."
            );

            return;
        }

        if (password.length < 8) {

            setMensaje(
                "La contraseña debe tener al menos 8 caracteres."
            );

            return;
        }

        const datos: RegistroClienteRequest = {
            nombre,
            email,
            empresa,
            password
        };

        try {

            setEnviando(true);

            await registroService.registrar(
                datos
            );

            setRegistroExitoso(true);

            setMensaje(
                "Cuenta creada correctamente. Ya puedes iniciar sesión."
            );

            setNombre("");
            setEmail("");
            setEmpresa("");
            setPassword("");
            setConfirmarPassword("");

        } catch (error) {

            if (error instanceof Error) {

                setMensaje(error.message);

            } else {

                setMensaje(
                    "No se pudo crear la cuenta."
                );
            }

        } finally {

            setEnviando(false);
        }
    };

    return (
        <main className="login-page">

            <section className="login-card">

                <h1>
                    Crear cuenta
                </h1>

                <p>
                    Regístrate para solicitar
                    soporte técnico.
                </p>

                <form
                    className="login-form"
                    onSubmit={handleSubmit}
                >

                    <div className="form-group">

                        <label htmlFor="nombre">
                            Nombre completo
                        </label>

                        <input
                            id="nombre"
                            type="text"
                            value={nombre}
                            onChange={(event) =>
                                setNombre(
                                    event.target.value
                                )
                            }
                            placeholder="Ej. Pedro Quispe"
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label htmlFor="email">
                            Correo electrónico
                        </label>

                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(event) =>
                                setEmail(
                                    event.target.value
                                )
                            }
                            placeholder="correo@empresa.com"
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label htmlFor="empresa">
                            Empresa
                        </label>

                        <input
                            id="empresa"
                            type="text"
                            value={empresa}
                            onChange={(event) =>
                                setEmpresa(
                                    event.target.value
                                )
                            }
                            placeholder="Ej. Empresa ABC"
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label htmlFor="password">
                            Contraseña
                        </label>

                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(event) =>
                                setPassword(
                                    event.target.value
                                )
                            }
                            placeholder="Mínimo 8 caracteres"
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label htmlFor="confirmarPassword">
                            Confirmar contraseña
                        </label>

                        <input
                            id="confirmarPassword"
                            type="password"
                            value={confirmarPassword}
                            onChange={(event) =>
                                setConfirmarPassword(
                                    event.target.value
                                )
                            }
                            placeholder="Repite tu contraseña"
                            required
                        />

                    </div>

                    {mensaje && (
                        <p
                            className={
                                registroExitoso
                                    ? "mensaje-exito"
                                    : "mensaje-error"
                            }
                        >
                            {mensaje}
                        </p>
                    )}

                    <button
                        className="btn-primary"
                        type="submit"
                        disabled={enviando}
                    >
                        {enviando
                            ? "Creando cuenta..."
                            : "Crear cuenta"}
                    </button>

                </form>

                <div className="registro-login">

                    <span>
                        ¿Ya tienes una cuenta?
                    </span>

                    <button
                        type="button"
                        className="btn-link"
                        onClick={onVolverLogin}
                    >
                        Iniciar sesión
                    </button>

                </div>

            </section>

        </main>
    );
}