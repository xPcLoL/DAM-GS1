"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { Anchor, Skull, Compass, ArrowRight } from "lucide-react";

export default function Home() {
  const [pirates, setPirates] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPirates = async () => {
      try {
        const response = await fetch("/api/producto");
        const data = await response.json();
        // ORDENAR POR RECOMPENSA DESCENDENTE
        const sorted = (data || []).sort((a: any, b: any) => (b.precio || 0) - (a.precio || 0));
        setPirates(sorted.slice(0, 5) || []);
      } catch (error) {
        console.error("Error fetching pirates:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchPirates();
  }, []);

  const formatBounty = (num: number) => num.toLocaleString("es-ES");

  return (
    <main className="min-h-screen text-[#2b1e16] overflow-x-hidden">
      {/* ─── HERO SECTION ─── */}
      <section className="relative h-screen w-full flex items-center">
        {/* Main Background Image */}
        <div 
          className="absolute inset-0 z-0 bg-cover bg-center bg-no-repeat opacity-60"
          style={{ 
            backgroundImage: "url('/fondo.jpeg')",
          }}
        >
          {/* Oceanic/Pirate Overlays */}
          <div className="absolute inset-0 bg-gradient-to-r from-[#003366]/40 via-transparent to-transparent" />
          <div className="absolute inset-0 bg-gradient-to-t from-transparent via-transparent to-transparent" />
        </div>

        <div className="relative z-10 max-w-7xl mx-auto px-6 w-full pt-20">
          <div className="flex items-center gap-3 mb-6 animate-fade-in">
             <div className="w-12 h-px bg-[#0077be]" />
             <span className="font-cinzel text-xs font-black uppercase tracking-[0.4em] text-[#003366]">Archivos del grand line</span>
          </div>

          <h1 className="font-bangers text-8xl md:text-[11rem] mb-6 tracking-tight leading-[0.8] text-[#003366] animate-slide-up drop-shadow-2xl">
            BOUNTY<br/>
            <span className="text-[#0077be]">BOARD</span>
          </h1>

          <p className="max-w-2xl text-xl md:text-2xl text-gray-700 mb-10 leading-relaxed font-fell italic animate-fade-in delay-200">
            "La riqueza, la fama y el poder... El hombre que lo consiguió todo en este mundo, el Rey de los Piratas, Gold Roger."
          </p>

          <div className="flex flex-wrap gap-5 animate-fade-in delay-300">
            <Link 
              href="/recompensas"
              className="flex items-center gap-3 px-12 py-5 bg-[#003366] text-white font-bangers text-2xl rounded hover:bg-[#0077be] transition-all transform hover:scale-105 shadow-2xl"
            >
              <Skull className="w-6 h-6" />
              SALA DE RECOMPENSAS
            </Link>
            <Link 
              href="/mugiwaras"
              className="flex items-center gap-3 px-12 py-5 bg-white/40 backdrop-blur-md text-[#003366] font-bangers text-2xl rounded hover:bg-white/60 transition-all border border-[#003366]/20"
            >
              <Compass className="w-6 h-6" />
              LOS MUGIWARA
            </Link>
          </div>
        </div>
      </section>

      {/* ─── TOP BOUNTIES SECTION ─── */}
      <section className="max-w-7xl mx-auto px-6 py-32">
        <div className="flex items-center justify-between mb-16">
          <div>
             <h2 className="font-bangers text-5xl tracking-tight text-[#003366] uppercase italic">LOS MÁS BUSCADOS</h2>
             <div className="rope-divider w-32" />
          </div>
          <Link href="/recompensas" className="flex items-center gap-2 font-bold text-xs text-[#0077be] hover:text-[#003366] transition-colors uppercase tracking-widest">
            Ver Tablero Completo <ArrowRight className="w-4 h-4" />
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-10">
          {loading ? (
             Array(5).fill(0).map((_, i) => (
              <div key={i} className="aspect-[2/3] bg-gray-50 rounded-xl animate-pulse" />
            ))
          ) : (
            pirates.map((p, idx) => (
              <Link 
                key={idx}
                href="/recompensas"
                className="wanted-poster group"
              >
                <div className="poster-portrait">
                  <img 
                    src={p.imagen} 
                    alt={p.nombre} 
                    className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110 filter sepia-[20%]" 
                  />
                </div>
                <div className="poster-info">
                   <h3 className="font-bangers text-2xl text-[#2b1e16] text-center w-full truncate leading-none mb-1">{p.nombre}</h3>
                   <p className="font-bangers text-xl text-[#2b1e16]">{formatBounty(p.precio)}</p>
                </div>
              </Link>
            ))
          )}
        </div>
      </section>

      {/* Footer Rope Decor */}
      <div className="rope-divider opacity-20" />
    </main>
  );
}
