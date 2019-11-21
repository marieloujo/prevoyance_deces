<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateBeneficesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('benefices', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('statut');
            $table->string('taux');
            $table->unsignedBigInteger('beneficiaire_id');

            $table->unsignedBigInteger('assure_id');

            $table->foreign('beneficiaire_id')
                ->references('id')
                ->on('beneficiaires')
                ->ondelete('cascade');
    
            $table->foreign('assure_id')
                ->references('id')
                ->on('assures')
                ->ondelete('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('benefices');
    }
}
