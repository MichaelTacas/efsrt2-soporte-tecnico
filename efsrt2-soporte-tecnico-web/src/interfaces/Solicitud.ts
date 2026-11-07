export interface Solicitud {
    id: number;

    titulo: string;
    descripcion: string;

    prioridad:
        | "BAJA"
        | "MEDIA"
        | "ALTA";

    gravedad:
        | "LEVE"
        | "MODERADA"
        | "GRAVE"
        | "CRITICA"
        | null;

    estado:
        | "PENDIENTE"
        | "EN_PROCESO"
        | "RESUELTA"
        | "CERRADA";

    // =========================================
    // CLIENTE
    // =========================================

    clienteId: number;
    clienteNombre: string;

    clienteEmpresa: string;
    clienteArea: string;
    clienteTelefono: string;

    // =========================================
    // TÉCNICO
    // =========================================

    tecnicoId: number | null;
    tecnicoNombre: string | null;

    // =========================================
    // FECHAS
    // =========================================

    fechaCreacion: string | null;
    fechaLimite: string | null;
    fechaInicioAtencion: string | null;
    fechaResolucion: string | null;
    fechaCierre: string | null;
}