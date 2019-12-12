<?php

namespace App\Http\Resources;

use App\Http\Resources\Collection\AssurerResource;
use App\Http\Resources\Collection\ClientResource;
use App\Models\Contrat;
use Carbon\Carbon;
use Illuminate\Http\Resources\Json\JsonResource;

class TransactionResources extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $contrat=Contrat::find($this->contrat_id);
        return [
            'id' => $this->id,
            'montant' => $this->montant,
            'created_at' => Carbon::parse($this->created_at)->format('Y-m-d h:m:s'),
            'contrat' => [             
                'id' => $contrat->id,
                'numero_contrat' => $contrat->numero_contrat,
                'numero_police_assurance' => $contrat->numero_police_assurance,
                'garantie' => $contrat->garantie,
                'prime' => $contrat->prime,
                'fin' => $contrat->fin == 1 ? true : false ,
                'valider' => $contrat->valider == 1 ? true : false ,
                'client' => new ClientResource($contrat->client),
                'assure' =>new AssurerResource($contrat->assure),
            ],
        ];
    }
}
