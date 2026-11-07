export interface ClasificarSolicitudRequest {
    gravedad:
        | "LEVE"
        | "MODERADA"
        | "GRAVE"
        | "CRITICA";

    tecnicoId: number;
}