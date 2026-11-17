"use client";
import { useEffect, useState } from 'react';
import { supabase } from '@/lib/supabase'; // Asegúrate de que la ruta sea correcta

export default function TestPage() {
    const [status, setStatus] = useState("Cargando...");
    const [data, setData] = useState<any[] | null>(null);

    useEffect(() => {
        async function comprobarConexion() {
            try {
                // Importante: extraemos data y error correctamente
                const { data, error } = await supabase
                    .from('productos')
                    .select('*')
                    .limit(1);

                if (error) {
                    setStatus("❌ Error de conexión: " + error.message);
                } else {
                    setStatus("✅ ¡Conectado con éxito!");
                    // Usamos una validación simple para que no de error
                    if (data) setData(data);
                }
            } catch (err: any) { // El :any quita el error de err.message rápidamente
                setStatus("❌ Error inesperado: " + (err.message || "Error desconocido"));
            }
        }

        comprobarConexion();
    }, []);

    return (
        <div className="p-8">
            <h1 className="text-2xl font-bold mb-4">Estado de Supabase:</h1>
            <p className={`p-4 rounded ${status.includes('✅') ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                {status}
            </p>
            {data && (
                <pre className="mt-4 bg-gray-900 p-4 rounded text-xs">
                    {JSON.stringify(data, null, 2)}
                </pre>
            )}
        </div>
    );
}