"use client";

import { useEffect, useState } from "react";
import { Ship, Anchor, Skull, Users, Star, X } from "lucide-react";
import { useAuth } from "@/components/AuthContext";
import { createBrowserClient } from '@supabase/ssr';

export default function SobreNosotros() {
  const [pirates, setPirates] = useState<any[]>([]);
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
    supabase.auth.getUser().then(({ data: { user } }) => setUser(user));
  }, []);

  useEffect(() => {
    const fetchPirates = async () => {
      try {
        const response = await fetch("/api/producto");
        const data = await response.json();
        const mugiwaras = (data || [])
          .filter((p: any) => p.categorias?.nombre === "Mugiwara")
          .sort((a: any, b: any) => (b.precio || 0) - (a.precio || 0));
        setPirates(mugiwaras);
      } catch (error) {
        console.error("Error:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchPirates();
  }, []);

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

  const totalBounty = pirates.reduce((acc, curr) => acc + (curr.precio || 0), 0);
  const formatBounty = (num: number) => num.toLocaleString("es-ES");

  return (
    <main className="min-h-screen pt-32 pb-20">
      <div 
        className="fixed inset-0 z-0 opacity-20 pointer-events-none bg-cover bg-center"
        style={{ backgroundImage: "url('/fondo-tripulacion.jpg')" }}
      />

      <div className="relative z-10 max-w-7xl mx-auto px-6">
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-4 mb-4">
             <Ship className="w-12 h-12 text-[#003366]" />
             <h1 className="font-bangers text-7xl text-[#003366] tracking-tight uppercase">LA TRIPULACIÓN</h1>
             <Ship className="w-12 h-12 text-[#003366]" />
          </div>
          <p className="font-fell italic text-2xl text-gray-500 max-w-3xl mx-auto">
            "No importa lo fuerte que seas, nadie sobrevive solo en el mar."
          </p>
          <div className="rope-divider max-w-sm mx-auto" />
        </div>

        {/* Stats Header */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-20">
           <div className="bg-[#fdf5e6] p-6 rounded-xl border-2 border-[#0077be]/20 flex items-center justify-between shadow-lg">
              <div>
                <p className="font-cinzel text-[10px] font-black text-gray-400 uppercase tracking-widest">Miembros</p>
                <p className="font-bangers text-4xl text-[#003366]">{loading ? "..." : pirates.length}</p>
              </div>
              <Users className="text-[#0077be] w-10 h-10 opacity-20" />
           </div>
           <div className="bg-[#fdf5e6] p-6 rounded-xl border-2 border-[#8B0000]/20 flex items-center justify-between shadow-lg">
              <div>
                <p className="font-cinzel text-[10px] font-black text-gray-400 uppercase tracking-widest">Poder de Combate</p>
                <p className="font-bangers text-3xl text-[#8B0000]">฿ {loading ? "..." : formatBounty(totalBounty)}</p>
              </div>
              <Skull className="text-[#8B0000] w-10 h-10 opacity-20" />
           </div>
           <div className="bg-[#fdf5e6] p-6 rounded-xl border-2 border-[#D4A017]/20 flex items-center justify-between shadow-lg">
              <div>
                <p className="font-cinzel text-[10px] font-black text-gray-400 uppercase tracking-widest">Navegación</p>
                <p className="font-bangers text-3xl text-[#D4A017]">NUEVO MUNDO</p>
              </div>
              <Anchor className="text-[#D4A017] w-10 h-10 opacity-20" />
           </div>
        </div>

        {/* Wanted Posters Grid (The Crew) */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-10">
          {loading ? (
             Array(5).fill(0).map((_, i) => <div key={i} className="aspect-[2/3] bg-white rounded-xl animate-pulse" />)
          ) : (
            pirates.map((p) => (
              <div 
                key={p.id_producto} 
                onClick={() => handleVerDetalles(p)}
                className="wanted-poster cursor-pointer group"
              >
                <div className="poster-portrait">
                  <img src={p.imagen} alt={p.nombre} className="w-full h-full object-cover filter sepia-[15%]" />
                </div>
                <div className="poster-info">
                   <h3 className="font-bangers text-xl text-[#2b1e16] text-center w-full truncate leading-none mb-1">{p.nombre}</h3>
                   <p className="font-bangers text-lg text-[#2b1e16]">{formatBounty(p.precio)}</p>
                </div>
                {/* Stamp */}
                <div className="absolute bottom-2 right-2 opacity-10 group-hover:opacity-40 transition-opacity">
                  <Anchor className="w-6 h-6 text-[#003366]" />
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
            <button onClick={() => setSelectedProducto(null)} className="absolute top-4 right-4 z-10 p-2 bg-black/10 rounded-full hover:bg-black/20 text-gray-500 hover:text-black"><X/></button>
            
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

              <div className="flex-grow overflow-y-auto space-y-4 pr-2 custom-scrollbar">
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
                 <button type="submit" disabled={enviandoResena} className="w-full py-2 bg-[#0077be] text-white font-bangers text-lg rounded-lg hover:bg-[#003366] disabled:opacity-50">
                   {enviandoResena ? 'ENVIANDO...' : 'ENVIAR VALORACIÓN'}
                 </button>
              </form>
            </div>
          </div>
        </div>
      )}
    </main>
  );
}
