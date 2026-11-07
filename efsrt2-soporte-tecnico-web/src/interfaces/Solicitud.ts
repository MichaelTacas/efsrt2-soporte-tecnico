export interface Solicitud {
    id: number;

    titulo: string;
    descripcion: string;

    prioridad: "BAJA" | "MEDIA" | "ALTA";

    gravedad:
        | "LEVE"
        | "MODERADA"
        | "GRAVE"
        | "CRITICA";

    estado:
        | "PENDIENTE"
        | "EN_PROCESO"
        | "RESUELTA"
        | "CERRADA";

    clienteId: number;
    clienteNombre: string;

    tecnicoId: number | null;
    tecnicoNombre: string | null;

    fechaCreacion: string | null;
    fechaLimite: string | null;
    fechaInicioAtencion: string | null;
    fechaResolucion: string | null;
    fechaCierre: string | null;
}