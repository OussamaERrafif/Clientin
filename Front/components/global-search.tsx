"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Search, User, MessageSquare, Building } from "lucide-react"
import { Command, CommandEmpty, CommandGroup, CommandInput, CommandItem, CommandList } from "@/components/ui/command"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"

const searchResults = [
  {
    type: "employee",
    title: "Sarah Khalil",
    subtitle: "Agent - Service Client",
    icon: User,
  },
  {
    type: "feedback",
    title: "Avis positif - Restaurant Al Fassia",
    subtitle: "Par Mohamed Benali • 5 étoiles",
    icon: MessageSquare,
  },
  {
    type: "restaurant",
    title: "Café Central",
    subtitle: "89 feedbacks • 4.6 étoiles",
    icon: Building,
  },
]

export function GlobalSearch() {
  const [open, setOpen] = useState(false)
  const [searchTerm, setSearchTerm] = useState("")

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="w-64 justify-start bg-gray-800 border-gray-700 text-gray-400 hover:bg-gray-700"
        >
          <Search className="mr-2 h-4 w-4" />
          Rechercher...
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-80 p-0 bg-gray-900 border-gray-800">
        <Command>
          <CommandInput
            placeholder="Rechercher employés, feedbacks, restaurants..."
            className="bg-gray-900 border-0 text-white"
          />
          <CommandList>
            <CommandEmpty className="text-gray-400 p-4">Aucun résultat trouvé.</CommandEmpty>
            <CommandGroup heading="Résultats" className="text-gray-300">
              {searchResults.map((result, index) => (
                <CommandItem key={index} className="flex items-center gap-3 p-3 text-white hover:bg-gray-800">
                  <result.icon className="h-4 w-4 text-gray-400" />
                  <div className="flex-1">
                    <p className="font-medium">{result.title}</p>
                    <p className="text-gray-400 text-sm">{result.subtitle}</p>
                  </div>
                  <Badge variant="outline" className="text-xs">
                    {result.type}
                  </Badge>
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  )
}
