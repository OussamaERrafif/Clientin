'use client'

import { useGetUsersQuery } from '@/lib/api'

export function ApiTest() {
  const { data, error, isLoading } = useGetUsersQuery()

  if (isLoading) return <div className="text-white">Loading API test...</div>
  if (error) return <div className="text-red-500">Error: API connection failed - this is expected if backend is not running</div>
  
  return (
    <div className="bg-gray-800 p-4 rounded-lg">
      <h3 className="text-white font-semibold mb-2">RTK Query Test</h3>
      <p className="text-green-500">✅ RTK Query is working!</p>
      <p className="text-gray-300 text-sm">Data: {JSON.stringify(data)}</p>
    </div>
  )
}
