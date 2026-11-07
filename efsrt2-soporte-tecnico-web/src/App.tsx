import { useState } from "react";

import { LoginPage } from "./pages/LoginPage";
import RegistroPage from "./pages/RegistroPage";

import SolicitudesPage from "./pages/SolicitudesPage";
import TecnicoSolicitudesPage from "./pages/TecnicoSolicitudesPage";
import ClienteSolicitudesPage from "./pages/ClienteSolicitudesPage";

import { AppHeader } from "./components/AppHeader";

import { authService } from "./services/authService";

function App() {

    const [autenticado, setAutenticado] =
        useState(
            authService.estaAutenticado()
        );

    const [mostrarRegistro, setMostrarRegistro] =
        useState(false);

    // =========================================
    // LOGIN EXITOSO
    // =========================================

    const manejarLoginExitoso = () => {

        setAutenticado(true);
        setMostrarRegistro(false);
    };

    // =========================================
    // CERRAR SESIÓN
    // =========================================

    const manejarCerrarSesion = () => {

        authService.cerrarSesion();

        setAutenticado(false);
        setMostrarRegistro(false);
    };

    // =========================================
    // USUARIO NO AUTENTICADO
    // =========================================

    if (!autenticado) {

        if (mostrarRegistro) {

            return (
                <RegistroPage
                    onVolverLogin={() =>
                        setMostrarRegistro(false)
                    }
                />
            );
        }

        return (
            <LoginPage
                onLoginExitoso={
                    manejarLoginExitoso
                }
                onCrearCuenta={() =>
                    setMostrarRegistro(true)
                }
            />
        );
    }

    // =========================================
    // USUARIO AUTENTICADO
    // =========================================

    const usuario =
        authService.obtenerUsuario();

    const renderContenido = () => {

        if (
            usuario?.rol ===
            "ADMINISTRADOR"
        ) {

            return (
                <SolicitudesPage />
            );
        }

        if (
            usuario?.rol ===
            "TECNICO"
        ) {

            return (
                <TecnicoSolicitudesPage />
            );
        }

        if (
            usuario?.rol ===
            "CLIENTE"
        ) {

            return (
                <ClienteSolicitudesPage />
            );
        }

        return (
            <main
                style={{
                    padding: "2rem"
                }}
            >
                <h1>
                    Acceso no disponible
                </h1>

                <p>
                    El usuario no tiene un rol
                    válido para acceder al sistema.
                </p>
            </main>
        );
    };

    return (
        <>
            <AppHeader
                onCerrarSesion={
                    manejarCerrarSesion
                }
            />

            {renderContenido()}
        </>
    );
}

export default App;