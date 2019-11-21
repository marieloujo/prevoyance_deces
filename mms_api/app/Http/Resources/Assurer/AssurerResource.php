<?php

namespace App\Http\Resources\Assurer;
use App\Http\Resources\UserResource;
use App\Http\Resources\Beneficiaire\BeneficiaireResource;
use App\Http\Resources\Client\ClientResource;
use App\Http\Resources\DocumentResource;
use Illuminate\Http\Resources\Json\JsonResource;

class AssurerResource extends JsonResource
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
            'profession' => $this->profession,
            'user' => new UserResource($this->user),
            'beneficiares' => BeneficiaireResource::collection($this->beneficiares),
            'souscripteurs' => ClientResource::collection($this->souscripteurs),
            'documents' => DocumentResource::collection($this->documents),
            
        ];
    }
}
