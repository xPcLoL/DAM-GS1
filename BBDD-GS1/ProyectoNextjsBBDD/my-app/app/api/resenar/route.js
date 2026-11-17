import { createServerClient } from '@supabase/ssr'
import { cookies } from 'next/headers'

export async function GET(request) {
  const { searchParams } = new URL(request.url)
  const productId = searchParams.get('id_producto')

  if (!productId) {
    return Response.json({ error: 'Falta el id_producto' }, { status: 400 })
  }

  const cookieStore = await cookies()
  const supabase = createServerClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY,
    {
      cookies: {
        get(name) {
          return cookieStore.get(name)?.value
        },
      },
    }
  )

  const { data, error } = await supabase
    .from('resenas')
    .select('*, usuarios(nombre)')
    .eq('id_producto', productId)
    .order('fecha_creacion', { ascending: false })

  if (error) {
    return Response.json({ error: error.message }, { status: 500 })
  }

  return Response.json(data)
}

export async function POST(request) {
  const cookieStore = await cookies()
  const supabase = createServerClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY,
    {
      cookies: {
        get(name) {
          return cookieStore.get(name)?.value
        },
        set(name, value, options) {
          cookieStore.set({ name, value, ...options })
        },
        remove(name, options) {
          cookieStore.set({ name, value: '', ...options })
        },
      },
    }
  )

  const { data: { session } } = await supabase.auth.getSession()

  if (!session) {
    return Response.json({ error: 'No autorizado' }, { status: 401 })
  }

  const body = await request.json()
  const { titulo, comentario, puntuacion, id_producto } = body

  const { data, error } = await supabase
    .from('resenas')
    .insert([
      {
        titulo,
        comentario,
        puntuacion,
        id_producto,
        id_usuario: session.user.id,
      },
    ])
    .select()

  if (error) {
    return Response.json({ error: error.message }, { status: 500 })
  }

  return Response.json(data[0])
}

export async function DELETE(request) {
  const { searchParams } = new URL(request.url)
  const id = searchParams.get('id')

  if (!id) {
    return Response.json({ error: 'Falta el id de la reseña' }, { status: 400 })
  }

  const cookieStore = await cookies()
  const supabase = createServerClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY,
    {
      cookies: {
        get(name) {
          return cookieStore.get(name)?.value
        },
      },
    }
  )

  const { data: { session } } = await supabase.auth.getSession()

  if (!session) {
    return Response.json({ error: 'No autorizado' }, { status: 401 })
  }

  // Solo permitimos borrar si es el dueño de la reseña
  const { error } = await supabase
    .from('resenas')
    .delete()
    .eq('id_resena', id)
    .eq('id_usuario', session.user.id)

  if (error) {
    return Response.json({ error: error.message }, { status: 500 })
  }

  return Response.json({ success: true })
}
