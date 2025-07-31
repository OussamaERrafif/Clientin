# Frontend Development Guide

## Table of Contents
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Development Setup](#development-setup)
- [Component Architecture](#component-architecture)
- [Styling and Theming](#styling-and-theming)
- [State Management](#state-management)
- [API Integration](#api-integration)
- [Routing and Navigation](#routing-and-navigation)
- [Form Handling](#form-handling)
- [Performance Optimization](#performance-optimization)
- [Testing](#testing)
- [Deployment](#deployment)

## Project Structure

```
Front/
├── app/                          # App Router (Next.js 13+)
│   ├── globals.css              # Global styles
│   ├── layout.tsx               # Root layout
│   ├── page.tsx                 # Home page
│   ├── loading.tsx              # Global loading UI
│   ├── admin/                   # Admin dashboard
│   │   └── page.tsx
│   ├── e-client/                # Client management
│   │   └── page.tsx
│   ├── employees/               # Employee management
│   │   ├── page.tsx
│   │   └── loading.tsx
│   ├── feedbacks/               # Feedback management
│   │   ├── page.tsx
│   │   └── loading.tsx
│   ├── insights/                # Business insights
│   │   └── page.tsx
│   ├── nfc-feedback/            # NFC feedback collection
│   │   └── page.tsx
│   └── settings/                # Application settings
│       └── page.tsx
├── components/                   # Reusable components
│   ├── ui/                      # Base UI components (shadcn/ui)
│   │   ├── button.tsx
│   │   ├── card.tsx
│   │   ├── dialog.tsx
│   │   ├── form.tsx
│   │   ├── input.tsx
│   │   ├── table.tsx
│   │   └── ...
│   ├── activity-calendar.tsx    # Custom components
│   ├── dynamic-feedback-chart.tsx
│   ├── employee-modal.tsx
│   ├── feedback-chart.tsx
│   ├── global-search.tsx
│   ├── insight-chart.tsx
│   ├── mobile-nav.tsx
│   ├── notification-center.tsx
│   ├── sidebar.tsx
│   └── theme-provider.tsx
├── hooks/                       # Custom React hooks
│   ├── use-mobile.tsx
│   └── use-toast.ts
├── lib/                         # Utility functions
│   └── utils.ts
├── public/                      # Static assets
│   ├── placeholder-logo.png
│   ├── placeholder-user.jpg
│   └── ...
├── styles/                      # Additional styles
│   └── globals.css
├── components.json              # shadcn/ui configuration
├── next.config.mjs             # Next.js configuration
├── package.json                # Dependencies
├── tailwind.config.ts          # Tailwind CSS configuration
└── tsconfig.json               # TypeScript configuration
```

## Technology Stack

### Core Technologies
- **Next.js 14+**: React framework with App Router
- **TypeScript**: Type-safe JavaScript
- **React 18+**: UI library with concurrent features
- **Tailwind CSS**: Utility-first CSS framework

### UI Components
- **shadcn/ui**: High-quality, accessible components
- **Radix UI**: Headless UI primitives
- **Lucide React**: Icon library
- **Recharts**: Data visualization library

### Development Tools
- **ESLint**: Code linting
- **Prettier**: Code formatting
- **Husky**: Git hooks
- **lint-staged**: Pre-commit formatting

## Development Setup

### Prerequisites
```bash
# Node.js 18 or higher
node --version

# pnpm (recommended) or npm
pnpm --version
```

### Installation
```bash
# Clone and navigate
cd Front

# Install dependencies
pnpm install

# Start development server
pnpm dev
```

### Environment Configuration

Create `.env.local`:
```env
# API Configuration
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1

# App Configuration
NEXT_PUBLIC_APP_NAME=Clientin Dashboard
NEXT_PUBLIC_APP_VERSION=1.0.0

# Development
NEXT_PUBLIC_ENV=development
NEXT_PUBLIC_LOG_LEVEL=debug
```

### Development Scripts
```json
{
  "scripts": {
    "dev": "next dev",
    "build": "next build",
    "start": "next start",
    "lint": "next lint",
    "type-check": "tsc --noEmit",
    "format": "prettier --write .",
    "clean": "rm -rf .next out"
  }
}
```

## Component Architecture

### Design System Structure

```typescript
// components/ui/button.tsx
import * as React from "react"
import { Slot } from "@radix-ui/react-slot"
import { cva, type VariantProps } from "class-variance-authority"
import { cn } from "@/lib/utils"

const buttonVariants = cva(
  "inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:opacity-50 disabled:pointer-events-none ring-offset-background",
  {
    variants: {
      variant: {
        default: "bg-primary text-primary-foreground hover:bg-primary/90",
        destructive: "bg-destructive text-destructive-foreground hover:bg-destructive/90",
        outline: "border border-input hover:bg-accent hover:text-accent-foreground",
        secondary: "bg-secondary text-secondary-foreground hover:bg-secondary/80",
        ghost: "hover:bg-accent hover:text-accent-foreground",
        link: "underline-offset-4 hover:underline text-primary",
      },
      size: {
        default: "h-10 py-2 px-4",
        sm: "h-9 px-3 rounded-md",
        lg: "h-11 px-8 rounded-md",
        icon: "h-10 w-10",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "default",
    },
  }
)
```

### Custom Components

```typescript
// components/feedback-chart.tsx
"use client"

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'

interface FeedbackChartProps {
  data: Array<{
    month: string
    positive: number
    negative: number
  }>
}

export function FeedbackChart({ data }: FeedbackChartProps) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Feedback Trends</CardTitle>
      </CardHeader>
      <CardContent>
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="positive" fill="#10b981" />
            <Bar dataKey="negative" fill="#ef4444" />
          </BarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  )
}
```

### Component Patterns

#### 1. Container/Presentational Pattern
```typescript
// Container Component
"use client"

import { useState, useEffect } from 'react'
import { EmployeeList } from './employee-list'
import { useEmployees } from '@/hooks/use-employees'

export function EmployeeContainer() {
  const { employees, loading, error, fetchEmployees } = useEmployees()

  useEffect(() => {
    fetchEmployees()
  }, [])

  if (loading) return <div>Loading...</div>
  if (error) return <div>Error: {error}</div>

  return <EmployeeList employees={employees} />
}

// Presentational Component
interface Employee {
  id: string
  name: string
  position: string
  performance: number
}

interface EmployeeListProps {
  employees: Employee[]
}

export function EmployeeList({ employees }: EmployeeListProps) {
  return (
    <div className="grid gap-4">
      {employees.map((employee) => (
        <EmployeeCard key={employee.id} employee={employee} />
      ))}
    </div>
  )
}
```

#### 2. Compound Component Pattern
```typescript
// components/data-table/index.tsx
export { DataTable } from './data-table'
export { DataTableHeader } from './data-table-header'
export { DataTableBody } from './data-table-body'
export { DataTableRow } from './data-table-row'
export { DataTableCell } from './data-table-cell'

// Usage
<DataTable>
  <DataTableHeader>
    <DataTableRow>
      <DataTableCell>Name</DataTableCell>
      <DataTableCell>Email</DataTableCell>
      <DataTableCell>Actions</DataTableCell>
    </DataTableRow>
  </DataTableHeader>
  <DataTableBody>
    {users.map((user) => (
      <DataTableRow key={user.id}>
        <DataTableCell>{user.name}</DataTableCell>
        <DataTableCell>{user.email}</DataTableCell>
        <DataTableCell>
          <Button>Edit</Button>
        </DataTableCell>
      </DataTableRow>
    ))}
  </DataTableBody>
</DataTable>
```

## Styling and Theming

### Tailwind CSS Configuration

```typescript
// tailwind.config.ts
import type { Config } from "tailwindcss"

const config: Config = {
  darkMode: ["class"],
  content: [
    './pages/**/*.{ts,tsx}',
    './components/**/*.{ts,tsx}',
    './app/**/*.{ts,tsx}',
    './src/**/*.{ts,tsx}',
  ],
  theme: {
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    extend: {
      colors: {
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
}

export default config
```

### CSS Variables (Dark/Light Theme)

```css
/* app/globals.css */
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer base {
  :root {
    --background: 0 0% 100%;
    --foreground: 222.2 84% 4.9%;
    --card: 0 0% 100%;
    --card-foreground: 222.2 84% 4.9%;
    --popover: 0 0% 100%;
    --popover-foreground: 222.2 84% 4.9%;
    --primary: 222.2 47.4% 11.2%;
    --primary-foreground: 210 40% 98%;
    --secondary: 210 40% 96%;
    --secondary-foreground: 222.2 47.4% 11.2%;
    --muted: 210 40% 96%;
    --muted-foreground: 215.4 16.3% 46.9%;
    --accent: 210 40% 96%;
    --accent-foreground: 222.2 47.4% 11.2%;
    --destructive: 0 84.2% 60.2%;
    --destructive-foreground: 210 40% 98%;
    --border: 214.3 31.8% 91.4%;
    --input: 214.3 31.8% 91.4%;
    --ring: 222.2 84% 4.9%;
    --radius: 0.5rem;
  }

  .dark {
    --background: 222.2 84% 4.9%;
    --foreground: 210 40% 98%;
    --card: 222.2 84% 4.9%;
    --card-foreground: 210 40% 98%;
    --popover: 222.2 84% 4.9%;
    --popover-foreground: 210 40% 98%;
    --primary: 210 40% 98%;
    --primary-foreground: 222.2 47.4% 11.2%;
    --secondary: 217.2 32.6% 17.5%;
    --secondary-foreground: 210 40% 98%;
    --muted: 217.2 32.6% 17.5%;
    --muted-foreground: 215 20.2% 65.1%;
    --accent: 217.2 32.6% 17.5%;
    --accent-foreground: 210 40% 98%;
    --destructive: 0 62.8% 30.6%;
    --destructive-foreground: 210 40% 98%;
    --border: 217.2 32.6% 17.5%;
    --input: 217.2 32.6% 17.5%;
    --ring: 212.7 26.8% 83.9%;
  }
}

@layer base {
  * {
    @apply border-border;
  }
  body {
    @apply bg-background text-foreground;
  }
}
```

### Theme Provider

```typescript
// components/theme-provider.tsx
"use client"

import * as React from "react"
import { ThemeProvider as NextThemesProvider } from "next-themes"
import { type ThemeProviderProps } from "next-themes/dist/types"

export function ThemeProvider({ children, ...props }: ThemeProviderProps) {
  return <NextThemesProvider {...props}>{children}</NextThemesProvider>
}

// Usage in layout.tsx
import { ThemeProvider } from "@/components/theme-provider"

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body>
        <ThemeProvider
          attribute="class"
          defaultTheme="system"
          enableSystem
          disableTransitionOnChange
        >
          {children}
        </ThemeProvider>
      </body>
    </html>
  )
}
```

## State Management

### Custom Hooks Pattern

```typescript
// hooks/use-employees.ts
import { useState, useCallback } from 'react'

interface Employee {
  id: string
  name: string
  email: string
  position: string
}

export function useEmployees() {
  const [employees, setEmployees] = useState<Employee[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchEmployees = useCallback(async () => {
    setLoading(true)
    setError(null)
    
    try {
      const response = await fetch('/api/v1/employees')
      if (!response.ok) throw new Error('Failed to fetch employees')
      
      const data = await response.json()
      setEmployees(data.content || data)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error')
    } finally {
      setLoading(false)
    }
  }, [])

  const createEmployee = useCallback(async (employee: Omit<Employee, 'id'>) => {
    try {
      const response = await fetch('/api/v1/employees', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(employee),
      })
      
      if (!response.ok) throw new Error('Failed to create employee')
      
      const newEmployee = await response.json()
      setEmployees(prev => [...prev, newEmployee])
      return newEmployee
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error')
      throw err
    }
  }, [])

  const updateEmployee = useCallback(async (id: string, updates: Partial<Employee>) => {
    try {
      const response = await fetch(`/api/v1/employees/${id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updates),
      })
      
      if (!response.ok) throw new Error('Failed to update employee')
      
      const updatedEmployee = await response.json()
      setEmployees(prev => prev.map(emp => emp.id === id ? updatedEmployee : emp))
      return updatedEmployee
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error')
      throw err
    }
  }, [])

  const deleteEmployee = useCallback(async (id: string) => {
    try {
      const response = await fetch(`/api/v1/employees/${id}`, {
        method: 'DELETE',
      })
      
      if (!response.ok) throw new Error('Failed to delete employee')
      
      setEmployees(prev => prev.filter(emp => emp.id !== id))
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error')
      throw err
    }
  }, [])

  return {
    employees,
    loading,
    error,
    fetchEmployees,
    createEmployee,
    updateEmployee,
    deleteEmployee,
  }
}
```

### Global State with Context

```typescript
// contexts/app-context.tsx
"use client"

import React, { createContext, useContext, useReducer, ReactNode } from 'react'

interface User {
  id: string
  name: string
  email: string
  role: string
}

interface AppState {
  user: User | null
  notifications: Array<{
    id: string
    message: string
    type: 'success' | 'error' | 'info'
  }>
  theme: 'light' | 'dark' | 'system'
}

type AppAction =
  | { type: 'SET_USER'; payload: User | null }
  | { type: 'ADD_NOTIFICATION'; payload: { message: string; type: 'success' | 'error' | 'info' } }
  | { type: 'REMOVE_NOTIFICATION'; payload: string }
  | { type: 'SET_THEME'; payload: 'light' | 'dark' | 'system' }

const initialState: AppState = {
  user: null,
  notifications: [],
  theme: 'system',
}

function appReducer(state: AppState, action: AppAction): AppState {
  switch (action.type) {
    case 'SET_USER':
      return { ...state, user: action.payload }
    case 'ADD_NOTIFICATION':
      return {
        ...state,
        notifications: [
          ...state.notifications,
          { id: Date.now().toString(), ...action.payload }
        ]
      }
    case 'REMOVE_NOTIFICATION':
      return {
        ...state,
        notifications: state.notifications.filter(n => n.id !== action.payload)
      }
    case 'SET_THEME':
      return { ...state, theme: action.payload }
    default:
      return state
  }
}

const AppContext = createContext<{
  state: AppState
  dispatch: React.Dispatch<AppAction>
} | null>(null)

export function AppProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(appReducer, initialState)

  return (
    <AppContext.Provider value={{ state, dispatch }}>
      {children}
    </AppContext.Provider>
  )
}

export function useApp() {
  const context = useContext(AppContext)
  if (!context) {
    throw new Error('useApp must be used within an AppProvider')
  }
  return context
}

// Helper hooks
export function useUser() {
  const { state, dispatch } = useApp()
  
  const setUser = (user: User | null) => {
    dispatch({ type: 'SET_USER', payload: user })
  }
  
  return { user: state.user, setUser }
}

export function useNotifications() {
  const { state, dispatch } = useApp()
  
  const addNotification = (message: string, type: 'success' | 'error' | 'info' = 'info') => {
    dispatch({ type: 'ADD_NOTIFICATION', payload: { message, type } })
  }
  
  const removeNotification = (id: string) => {
    dispatch({ type: 'REMOVE_NOTIFICATION', payload: id })
  }
  
  return {
    notifications: state.notifications,
    addNotification,
    removeNotification,
  }
}
```

## API Integration

### API Client

```typescript
// lib/api.ts
class ApiClient {
  private baseURL: string
  private defaultHeaders: Record<string, string>

  constructor(baseURL: string) {
    this.baseURL = baseURL
    this.defaultHeaders = {
      'Content-Type': 'application/json',
    }
  }

  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseURL}${endpoint}`
    
    const config: RequestInit = {
      headers: {
        ...this.defaultHeaders,
        ...options.headers,
      },
      ...options,
    }

    try {
      const response = await fetch(url, config)
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }

      const data = await response.json()
      return data
    } catch (error) {
      console.error('API request failed:', error)
      throw error
    }
  }

  // Users
  async getUsers(params?: { page?: number; size?: number; sort?: string }) {
    const searchParams = new URLSearchParams()
    if (params?.page !== undefined) searchParams.append('page', params.page.toString())
    if (params?.size !== undefined) searchParams.append('size', params.size.toString())
    if (params?.sort) searchParams.append('sort', params.sort)
    
    const query = searchParams.toString()
    return this.request(`/users${query ? `?${query}` : ''}`)
  }

  async getUser(id: string) {
    return this.request(`/users/${id}`)
  }

  async createUser(user: any) {
    return this.request('/users', {
      method: 'POST',
      body: JSON.stringify(user),
    })
  }

  async updateUser(id: string, updates: any) {
    return this.request(`/users/${id}`, {
      method: 'PATCH',
      body: JSON.stringify(updates),
    })
  }

  async deleteUser(id: string) {
    return this.request(`/users/${id}`, {
      method: 'DELETE',
    })
  }

  // Employees
  async getEmployees() {
    return this.request('/employeeProfiles')
  }

  async createEmployee(employee: any) {
    return this.request('/employeeProfiles', {
      method: 'POST',
      body: JSON.stringify(employee),
    })
  }

  // Feedback
  async getFeedback() {
    return this.request('/feedback')
  }

  async createFeedback(feedback: any) {
    return this.request('/feedback', {
      method: 'POST',
      body: JSON.stringify(feedback),
    })
  }
}

export const apiClient = new ApiClient(process.env.NEXT_PUBLIC_API_URL || '')
```

### React Query Integration (Optional)

```typescript
// hooks/use-api.ts
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { apiClient } from '@/lib/api'

// Users
export function useUsers(params?: { page?: number; size?: number }) {
  return useQuery({
    queryKey: ['users', params],
    queryFn: () => apiClient.getUsers(params),
  })
}

export function useUser(id: string) {
  return useQuery({
    queryKey: ['users', id],
    queryFn: () => apiClient.getUser(id),
    enabled: !!id,
  })
}

export function useCreateUser() {
  const queryClient = useQueryClient()
  
  return useMutation({
    mutationFn: apiClient.createUser,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['users'] })
    },
  })
}

export function useUpdateUser() {
  const queryClient = useQueryClient()
  
  return useMutation({
    mutationFn: ({ id, updates }: { id: string; updates: any }) =>
      apiClient.updateUser(id, updates),
    onSuccess: (_, { id }) => {
      queryClient.invalidateQueries({ queryKey: ['users'] })
      queryClient.invalidateQueries({ queryKey: ['users', id] })
    },
  })
}
```

## Routing and Navigation

### App Router Structure

```typescript
// app/layout.tsx
import { Sidebar } from '@/components/sidebar'
import { AppProvider } from '@/contexts/app-context'

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body>
        <AppProvider>
          <div className="flex h-screen">
            <Sidebar />
            <main className="flex-1 overflow-auto">
              {children}
            </main>
          </div>
        </AppProvider>
      </body>
    </html>
  )
}
```

### Navigation Component

```typescript
// components/sidebar.tsx
"use client"

import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { cn } from '@/lib/utils'
import { Button } from '@/components/ui/button'
import {
  Home,
  Users,
  MessageSquare,
  BarChart3,
  Settings,
  UserCog,
} from 'lucide-react'

const navigation = [
  { name: 'Dashboard', href: '/', icon: Home },
  { name: 'Employees', href: '/employees', icon: Users },
  { name: 'Feedback', href: '/feedbacks', icon: MessageSquare },
  { name: 'Clients', href: '/e-client', icon: UserCog },
  { name: 'Insights', href: '/insights', icon: BarChart3 },
  { name: 'Settings', href: '/settings', icon: Settings },
]

export function Sidebar() {
  const pathname = usePathname()

  return (
    <div className="flex h-full w-64 flex-col bg-gray-900">
      <div className="flex h-16 items-center px-6">
        <h1 className="text-xl font-bold text-white">Clientin</h1>
      </div>
      <nav className="flex-1 space-y-1 px-3 py-4">
        {navigation.map((item) => {
          const isActive = pathname === item.href
          return (
            <Link key={item.name} href={item.href}>
              <Button
                variant={isActive ? "secondary" : "ghost"}
                className={cn(
                  "w-full justify-start text-left",
                  isActive
                    ? "bg-gray-700 text-white"
                    : "text-gray-300 hover:bg-gray-700 hover:text-white"
                )}
              >
                <item.icon className="mr-3 h-4 w-4" />
                {item.name}
              </Button>
            </Link>
          )
        })}
      </nav>
    </div>
  )
}
```

### Protected Routes

```typescript
// components/protected-route.tsx
"use client"

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useUser } from '@/contexts/app-context'

interface ProtectedRouteProps {
  children: React.ReactNode
  requireAuth?: boolean
  requiredRole?: string[]
}

export function ProtectedRoute({
  children,
  requireAuth = true,
  requiredRole,
}: ProtectedRouteProps) {
  const { user } = useUser()
  const router = useRouter()

  useEffect(() => {
    if (requireAuth && !user) {
      router.push('/login')
      return
    }

    if (requiredRole && user && !requiredRole.includes(user.role)) {
      router.push('/unauthorized')
      return
    }
  }, [user, requireAuth, requiredRole, router])

  if (requireAuth && !user) {
    return <div>Loading...</div>
  }

  if (requiredRole && user && !requiredRole.includes(user.role)) {
    return <div>Unauthorized</div>
  }

  return <>{children}</>
}

// Usage in page
export default function AdminPage() {
  return (
    <ProtectedRoute requiredRole={['ADMIN']}>
      <div>Admin content</div>
    </ProtectedRoute>
  )
}
```

## Form Handling

### React Hook Form Integration

```typescript
// components/employee-form.tsx
"use client"

import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { Button } from '@/components/ui/button'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

const employeeSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters'),
  email: z.string().email('Invalid email address'),
  position: z.string().min(1, 'Position is required'),
  department: z.string().min(1, 'Department is required'),
  hireDate: z.string().refine((date) => !isNaN(Date.parse(date)), {
    message: 'Invalid date format',
  }),
})

type EmployeeFormData = z.infer<typeof employeeSchema>

interface EmployeeFormProps {
  initialData?: Partial<EmployeeFormData>
  onSubmit: (data: EmployeeFormData) => Promise<void>
  isLoading?: boolean
}

export function EmployeeForm({ initialData, onSubmit, isLoading }: EmployeeFormProps) {
  const form = useForm<EmployeeFormData>({
    resolver: zodResolver(employeeSchema),
    defaultValues: initialData,
  })

  const handleSubmit = async (data: EmployeeFormData) => {
    try {
      await onSubmit(data)
      form.reset()
    } catch (error) {
      console.error('Form submission error:', error)
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-6">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <Input placeholder="Enter employee name" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" placeholder="Enter email address" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="position"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Position</FormLabel>
              <FormControl>
                <Input placeholder="Enter position" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="department"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Department</FormLabel>
              <Select onValueChange={field.onChange} defaultValue={field.value}>
                <FormControl>
                  <SelectTrigger>
                    <SelectValue placeholder="Select department" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <SelectItem value="sales">Sales</SelectItem>
                  <SelectItem value="marketing">Marketing</SelectItem>
                  <SelectItem value="engineering">Engineering</SelectItem>
                  <SelectItem value="hr">Human Resources</SelectItem>
                </SelectContent>
              </Select>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="hireDate"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Hire Date</FormLabel>
              <FormControl>
                <Input type="date" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit" disabled={isLoading}>
          {isLoading ? 'Saving...' : 'Save Employee'}
        </Button>
      </form>
    </Form>
  )
}
```

## Performance Optimization

### Code Splitting and Lazy Loading

```typescript
// app/admin/page.tsx
import dynamic from 'next/dynamic'
import { Suspense } from 'react'

// Lazy load heavy components
const AdminDashboard = dynamic(() => import('@/components/admin-dashboard'), {
  loading: () => <div>Loading dashboard...</div>,
  ssr: false,
})

const DataVisualization = dynamic(
  () => import('@/components/data-visualization'),
  {
    loading: () => <div>Loading charts...</div>,
  }
)

export default function AdminPage() {
  return (
    <div>
      <h1>Admin Dashboard</h1>
      <Suspense fallback={<div>Loading...</div>}>
        <AdminDashboard />
      </Suspense>
      <Suspense fallback={<div>Loading charts...</div>}>
        <DataVisualization />
      </Suspense>
    </div>
  )
}
```

### Image Optimization

```typescript
// components/employee-avatar.tsx
import Image from 'next/image'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'

interface EmployeeAvatarProps {
  src?: string
  name: string
  size?: 'sm' | 'md' | 'lg'
}

const sizeMap = {
  sm: { width: 32, height: 32 },
  md: { width: 48, height: 48 },
  lg: { width: 64, height: 64 },
}

export function EmployeeAvatar({ src, name, size = 'md' }: EmployeeAvatarProps) {
  const { width, height } = sizeMap[size]
  const initials = name
    .split(' ')
    .map(n => n[0])
    .join('')
    .toUpperCase()

  return (
    <Avatar style={{ width, height }}>
      {src ? (
        <Image
          src={src}
          alt={name}
          width={width}
          height={height}
          className="rounded-full object-cover"
          priority={size === 'lg'}
        />
      ) : (
        <AvatarFallback>{initials}</AvatarFallback>
      )}
    </Avatar>
  )
}
```

### Memoization

```typescript
// components/employee-list.tsx
import { memo, useMemo } from 'react'

interface Employee {
  id: string
  name: string
  department: string
  performance: number
}

interface EmployeeListProps {
  employees: Employee[]
  filter: string
  sortBy: 'name' | 'department' | 'performance'
}

export const EmployeeList = memo(function EmployeeList({
  employees,
  filter,
  sortBy,
}: EmployeeListProps) {
  const filteredAndSortedEmployees = useMemo(() => {
    let filtered = employees.filter(employee =>
      employee.name.toLowerCase().includes(filter.toLowerCase()) ||
      employee.department.toLowerCase().includes(filter.toLowerCase())
    )

    return filtered.sort((a, b) => {
      switch (sortBy) {
        case 'name':
          return a.name.localeCompare(b.name)
        case 'department':
          return a.department.localeCompare(b.department)
        case 'performance':
          return b.performance - a.performance
        default:
          return 0
      }
    })
  }, [employees, filter, sortBy])

  return (
    <div className="grid gap-4">
      {filteredAndSortedEmployees.map((employee) => (
        <EmployeeCard key={employee.id} employee={employee} />
      ))}
    </div>
  )
})

const EmployeeCard = memo(function EmployeeCard({ employee }: { employee: Employee }) {
  return (
    <div className="p-4 border rounded-lg">
      <h3>{employee.name}</h3>
      <p>{employee.department}</p>
      <p>Performance: {employee.performance}/5</p>
    </div>
  )
})
```

## Testing

### Unit Testing with Jest and Testing Library

```typescript
// __tests__/components/employee-form.test.tsx
import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { EmployeeForm } from '@/components/employee-form'

const mockOnSubmit = jest.fn()

describe('EmployeeForm', () => {
  beforeEach(() => {
    mockOnSubmit.mockClear()
  })

  it('renders all form fields', () => {
    render(<EmployeeForm onSubmit={mockOnSubmit} />)
    
    expect(screen.getByLabelText(/name/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/position/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/department/i)).toBeInTheDocument()
    expect(screen.getByLabelText(/hire date/i)).toBeInTheDocument()
  })

  it('submits form with valid data', async () => {
    const user = userEvent.setup()
    render(<EmployeeForm onSubmit={mockOnSubmit} />)
    
    await user.type(screen.getByLabelText(/name/i), 'John Doe')
    await user.type(screen.getByLabelText(/email/i), 'john@example.com')
    await user.type(screen.getByLabelText(/position/i), 'Developer')
    await user.selectOptions(screen.getByLabelText(/department/i), 'engineering')
    await user.type(screen.getByLabelText(/hire date/i), '2024-01-01')
    
    await user.click(screen.getByRole('button', { name: /save employee/i }))
    
    await waitFor(() => {
      expect(mockOnSubmit).toHaveBeenCalledWith({
        name: 'John Doe',
        email: 'john@example.com',
        position: 'Developer',
        department: 'engineering',
        hireDate: '2024-01-01',
      })
    })
  })

  it('shows validation errors for invalid data', async () => {
    const user = userEvent.setup()
    render(<EmployeeForm onSubmit={mockOnSubmit} />)
    
    await user.click(screen.getByRole('button', { name: /save employee/i }))
    
    await waitFor(() => {
      expect(screen.getByText(/name must be at least 2 characters/i)).toBeInTheDocument()
      expect(screen.getByText(/invalid email address/i)).toBeInTheDocument()
      expect(screen.getByText(/position is required/i)).toBeInTheDocument()
    })
    
    expect(mockOnSubmit).not.toHaveBeenCalled()
  })
})
```

### Integration Testing

```typescript
// __tests__/pages/employees.test.tsx
import { render, screen, waitFor } from '@testing-library/react'
import { rest } from 'msw'
import { setupServer } from 'msw/node'
import EmployeesPage from '@/app/employees/page'

const server = setupServer(
  rest.get('/api/v1/employeeProfiles', (req, res, ctx) => {
    return res(
      ctx.json({
        content: [
          {
            id: '1',
            name: 'John Doe',
            email: 'john@example.com',
            position: 'Developer',
            department: 'Engineering',
          },
        ],
      })
    )
  })
)

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())

describe('EmployeesPage', () => {
  it('displays employee list', async () => {
    render(<EmployeesPage />)
    
    await waitFor(() => {
      expect(screen.getByText('John Doe')).toBeInTheDocument()
      expect(screen.getByText('Developer')).toBeInTheDocument()
      expect(screen.getByText('Engineering')).toBeInTheDocument()
    })
  })
})
```

### E2E Testing with Playwright

```typescript
// e2e/employee-management.spec.ts
import { test, expect } from '@playwright/test'

test.describe('Employee Management', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/employees')
  })

  test('should create new employee', async ({ page }) => {
    await page.click('text=Add Employee')
    
    await page.fill('[name="name"]', 'Jane Smith')
    await page.fill('[name="email"]', 'jane@example.com')
    await page.fill('[name="position"]', 'Designer')
    await page.selectOption('[name="department"]', 'marketing')
    await page.fill('[name="hireDate"]', '2024-01-15')
    
    await page.click('text=Save Employee')
    
    await expect(page.locator('text=Jane Smith')).toBeVisible()
  })

  test('should edit existing employee', async ({ page }) => {
    await page.click('[data-testid="employee-1"] button[aria-label="Edit"]')
    
    await page.fill('[name="position"]', 'Senior Developer')
    await page.click('text=Save Employee')
    
    await expect(page.locator('text=Senior Developer')).toBeVisible()
  })

  test('should delete employee', async ({ page }) => {
    await page.click('[data-testid="employee-1"] button[aria-label="Delete"]')
    await page.click('text=Confirm Delete')
    
    await expect(page.locator('[data-testid="employee-1"]')).not.toBeVisible()
  })
})
```

This completes the comprehensive frontend development guide for the Clientin application. The guide covers all aspects of modern React/Next.js development including project structure, component architecture, styling, state management, API integration, routing, forms, performance optimization, and testing strategies.
