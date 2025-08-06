import type React from "react"
import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"
import { Sidebar } from "@/components/sidebar"
import { ReduxProvider } from "@/components/redux-provider"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
  title: "Clientin Dashboard",
  description: "Modern customer feedback management platform",
    generator: 'v0.dev'
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="fr">
      <body className={inter.className}>
        <ReduxProvider>
          <div className="flex h-screen bg-black">
            <Sidebar />
            {children}
          </div>
        </ReduxProvider>
      </body>
    </html>
  )
}
