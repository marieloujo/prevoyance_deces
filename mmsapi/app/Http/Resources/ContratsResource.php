<?php

namespace App\Http\Resources;

use App\Http\Resources\Collection\AssurerResource;
use App\Http\Resources\Collection\ClientResource;
use App\Http\Resources\Collection\DocumentResource;
use App\Http\Resources\Collection\MarchandResource;
use App\Http\Resources\Collection\PortefeuilleResource;
use Illuminate\Http\Resources\Json\JsonResource;

class ContratsResource extends JsonResource
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
            'numero_contrat' => $this->numero_contrat,
            'garantie' => $this->garantie,
            'prime' => $this->prime,
            'duree' => $this->duree,
            'date_debut' => $this->date_debut,
            'date_echeance' => $this->date_echeance,
            'date_effet' => $this->date_effet,
            'date_fin' => $this->date_fin,
            'fin' => $this->fin == 1 ? true : false ,
            'valider' => $this->valider == 1 ? true : false ,
            'marchand' => new MarchandResource($this->marchand),
            'client' => new ClientResource($this->client),
            'assure' =>new AssurerResource($this->assure),
            'benefices' => $this->beneficiaires,
            'portefeuilles' => $this->portefeuilles,
            'documents' => DocumentResource::collection($this->documents),
        ];
    }
}
