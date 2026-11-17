"use client";

import { useEffect, useState } from "react";
import { useAuth } from "@/components/AuthContext";
import { Search, Star, MessageSquare, Send, X, Anchor, Trash2 } from "lucide-react";
import { createBrowserClient } from '@supabase/ssr'

export default function TiendaPage() {
  const [productos, setProductos] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedProducto, setSelectedProducto] = useState<any>(null);
  const [user, setUser] = useState<any>(null);
  const { openLoginModal } = useAuth();
  
  const supabase = createBrowserClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL!,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY!
  )

  const [resenas, setResenas] = useState<any[]>([]);
  const [nuevaResena, setNuevaResena] = useState({ titulo: '', comentario: '', puntuacion: 5 });
  const [enviandoResena, setEnviandoResena] = useState(false);

  useEffect(() => {
    fetchProductos();
    supabase.auth.getUser().then(({ data: { user } }) => setUser(user));
  }, []);

  const fetchProductos = async () => {
    try {
      const response = await fetch("/api/producto");
      const data = await response.json();
      // ORDENAR POR RECOMPENSA (BOUNTY) DESCENDENTE
      const sorted = (data || []).sort((a: any, b: any) => (b.precio || 0) - (a.precio || 0));
      setProductos(sorted);
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchResenas = async (idProducto: number) => {
    try {
      const response = await fetch(`/api/resenar?id_producto=${idProducto}`);
      const data = await response.json();
      setResenas(data || []);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleVerDetalles = (producto: any) => {
    setSelectedProducto(producto);
    fetchResenas(producto.id_producto);
  };

  const handleEliminarResena = async (idResena: string) => {
    if (!confirm("¿Seguro que quieres borrar este informe?")) return;
    
    try {
      const response = await fetch(`/api/resenar?id=${idResena}`, {
        method: "DELETE"
      });
      if (response.ok) {
        setResenas(resenas.filter(r => r.id_resena !== idResena));
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleEnviarResena = async (e: React.FormEvent) => {
    e.preventDefault();
    setEnviandoResena(true);
    try {
      const response = await fetch("/api/resenar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ...nuevaResena,
          id_producto: selectedProducto.id_producto
        }),
      });

      if (!response.ok) throw new Error("No autorizado");
      await fetchResenas(selectedProducto.id_producto);
      setNuevaResena({ titulo: '', comentario: '', puntuacion: 5 });
    } catch (error) {
      openLoginModal();
    } finally {
      setEnviandoResena(false);
    }
  };

  const formatBounty = (num: number) => num.toLocaleString("es-ES");

  return (
    <main className="min-h-screen pt-32 pb-20">
      <div 
        className="fixed inset-0 z-0 opacity-20 pointer-events-none bg-cover bg-center"
        style={{ backgroundImage: "url('/fondo-sala-recompensas.jpg')" }}
      />

      <div className="relative z-10 max-w-7xl mx-auto px-6">
        <div className="text-center mb-16">
          <h1 className="font-bangers text-7xl text-[#003366] mb-2 tracking-tight drop-shadow-md">SALA DE RECOMPENSAS</h1>
          <p className="font-fell italic text-gray-500 text-xl">El registro oficial de los forajidos más buscados del mundo.</p>
          <div className="rope-divider max-w-xs mx-auto" />
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-12">
          {loading ? (
             Array(8).fill(0).map((_, i) => (
              <div key={i} className="aspect-[2/3] bg-white rounded-xl animate-pulse" />
            ))
          ) : (
            productos.map((p) => (
              <div 
                key={p.id_producto}
                onClick={() => handleVerDetalles(p)}
                className="wanted-poster cursor-pointer group"
              >
                <div className="poster-portrait">
                  <img 
                    src={p.imagen} 
                    alt={p.nombre} 
                    className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 filter sepia-[20%]" 
                  />
                </div>
                <div className="poster-info">
                   <h3 className="font-bangers text-3xl text-[#2b1e16] text-center w-full truncate leading-none mb-2">{p.nombre}</h3>
                   <p className="font-bangers text-2xl text-[#2b1e16]">{formatBounty(p.precio)}</p>
                </div>
                
                {/* Stamp */}
                <div className="absolute bottom-4 right-4 opacity-10 group-hover:opacity-40 transition-opacity">
                  <Anchor className="w-8 h-8 text-[#003366]" />
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      {/* MODAL DETALLES */}
      {selectedProducto && (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4">
          <div className="absolute inset-0 bg-black/60 backdrop-blur-sm" onClick={() => setSelectedProducto(null)} />
          <div className="relative w-full max-w-4xl bg-[#fdf5e6] rounded-2xl overflow-hidden flex flex-col md:flex-row shadow-2xl h-[90vh]">
            <button onClick={() => setSelectedProducto(null)} className="absolute top-4 right-4 z-10 p-2 bg-black/10 rounded-full hover:bg-black/20"><X/></button>
            
            <div className="w-full md:w-1/2 p-8 bg-[#f5deb3]/30 flex flex-col items-center justify-center border-r-2 border-[#e2e8f0]">
               <div className="wanted-poster w-full max-w-[300px]">
                <div className="poster-portrait">
                  <img src={selectedProducto.imagen} className="w-full h-full object-cover" />
                </div>
                <div className="poster-info">
                  <p className="font-bangers text-2xl text-center w-full truncate leading-none mb-1">{selectedProducto.nombre}</p>
                  <p className="font-bangers text-xl text-center">{formatBounty(selectedProducto.precio)}</p>
                </div>
               </div>
            </div>

            <div className="w-full md:w-1/2 p-8 flex flex-col">
              <h3 className="font-bangers text-3xl text-[#003366] mb-6 border-b-2 border-[#0077be]/20 pb-2">EXPEDIENTE OFICIAL</h3>
              
              {/* DESCRIPCIÓN DEL PRODUCTO */}
              <div className="mb-6 p-4 bg-[#f5deb3]/20 rounded-xl border-l-4 border-[#0077be]">
                <h4 className="font-cinzel text-[10px] font-black tracking-widest text-[#0077be] mb-2 uppercase">Antecedentes</h4>
                <p className="text-sm font-fell italic text-gray-700 leading-relaxed">
                  {selectedProducto.descripcion || "No hay registros adicionales en los archivos de la Marina."}
                </p>
              </div>

              <div className="flex-grow overflow-y-auto space-y-4 pr-2">
                {resenas.map((r, i) => (
                  <div key={i} className="bg-white/50 p-4 rounded-xl border border-gray-200 relative group/resena">
                    {user?.id === r.id_usuario && (
                      <button 
                        onClick={() => handleEliminarResena(r.id_resena)}
                        className="absolute top-2 right-2 text-red-500 hover:text-red-700 transition-colors opacity-0 group-hover/resena:opacity-100 p-1"
                        title="Eliminar valoración"
                      >
                        <X className="w-4 h-4" />
                      </button>
                    )}
                    <div className="flex justify-between items-start mb-2">
                      <p className="font-bold text-sm text-[#003366]">{r.usuarios?.nombre || "Pirata"}</p>
                      <div className="flex gap-0.5">
                        {Array(5).fill(0).map((_, idx) => <Star key={idx} className={`w-3 h-3 ${idx < r.puntuacion ? 'text-yellow-500 fill-current' : 'text-gray-200'}`} />)}
                      </div>
                    </div>
                    <p className="text-xs font-bold mb-1">{r.titulo}</p>
                    <p className="text-xs text-gray-600">{r.comentario}</p>
                  </div>
                ))}
              </div>
              <form onSubmit={handleEnviarResena} className="mt-6 pt-6 border-t border-gray-200 space-y-3">
                 <input value={nuevaResena.titulo} onChange={e => setNuevaResena({...nuevaResena, titulo: e.target.value})} placeholder="Resumen" className="w-full p-2 text-sm rounded-lg border border-gray-200" required />
                 <textarea value={nuevaResena.comentario} onChange={e => setNuevaResena({...nuevaResena, comentario: e.target.value})} placeholder="Comentario..." className="w-full p-2 text-sm rounded-lg border border-gray-200 h-20 resize-none" required />
                 <button type="submit" className="w-full py-2 bg-[#0077be] text-white font-bangers text-lg rounded-lg hover:bg-[#003366]">ENVIAR VALORACIÓN</button>
              </form>
            </div>
          </div>
        </div>
      )}
    </main>
  );
}
