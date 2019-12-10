<?php

namespace App\Http\Resources\Marchand;

use App\Http\Resources\Marchand\ContratResource;
use App\Models\Contrat;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Carbon;

class PortefeuilleResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'montant' => $this->montant,
            'created_at' => Carbon::parse($this->created_at)->format('Y-m-d h:m:s'),
            'contrat' => new ContratResource(Contrat::find($this->contrat_id)),
        ];
    }
}
