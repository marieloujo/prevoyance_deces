<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Portefeuille extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'id','montant','contrat_id','marchand_id',
    ];
    
    public function contrat(){
        return $this->belongsTo('App\Models\Contrat','contrat_id');
    }
    
    public function marchand(){
        return $this->belongsTo('App\Models\Marchand','marchand_id');
    }
    
    
}
